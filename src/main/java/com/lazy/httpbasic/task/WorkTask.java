package com.lazy.httpbasic.task;


import com.lazy.httpbasic.conf.Config;
import com.lazy.httpbasic.bean.*;
import com.lazy.httpbasic.util.ExceptionUtil;
import com.lazy.httpbasic.util.HttpBasicUtil;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class WorkTask implements Runnable {


    private Socket socket;

    public WorkTask(Socket socket) {
        this.socket = socket;
    }


    private void response401(HttpRequest httpRequest, PrintWriter out) {
        HttpResponse response = HttpResponseParser.ofResponse(httpRequest, null);
        HttpDigest digest = HttpBasicUtil.ofDigestResponse(httpRequest);
        response.setCode(401);
        response.setStatus("Unauthorized");
        response.setHeader("WWW-Authenticate", digest.ofString());
        //响应HttpResponse
        out.println(HttpResponseParser.ofResponseStr(response));
    }

    @Override
    public void run() {
        PrintWriter out = null;
        //解析HttpRequest
        HttpRequest httpRequest = null;
        try {
            httpRequest = HttpRequestParser.parse2request(socket.getInputStream());
            //创建响应流对象
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            //创建http digest
            HttpDigest digestRequest = HttpBasicUtil.ofDigestRequest(httpRequest);
            if (digestRequest == null) {
                this.response401(httpRequest, out);
                return;
            }
            //校验http basic 超时时间
            String decodeNonce = HttpBasicUtil.base64EncodeNonce(digestRequest.getNonce());
            long nonce = Long.parseLong(decodeNonce.split("::")[0]);
            long currentTime = System.currentTimeMillis();
            if (currentTime - nonce > Config.CONSOLE_HTTP_SESSION_TIME) {
                this.response401(httpRequest, out);
                return;
            }
            //检查用户名称是否正确
            if (!Config.CONSOLE_HTTP_BASIC_USERNAME.equals(digestRequest.getUsername())) {
                this.response401(httpRequest, out);
                return;
            }
            //检查密码是否匹配
            if (!digestRequest.getResponse().equals(digestRequest.md5())) {
                this.response401(httpRequest, out);
                return;
            }

            //Authentication-Info
            HttpResponse response = HttpResponseParser.ofResponse(httpRequest, this.createSuccessHtml());
//            response.setHeader("Authentication-Info", resp.ofString());
            //响应HttpResponse
            out.println(HttpResponseParser.ofResponseStr(response));
        } catch (Exception e) {
            if (httpRequest != null && out != null) {
                HttpResponse response = HttpResponseParser.ofResponse(httpRequest, "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">" +
                        "<title>Title</title></head><body><h3>Internal Server Error</h3><p style='white-space: pre-line;'>" + ExceptionUtil.getStackTrace(e) + "</p></body></html>");
                out.println(HttpResponseParser.ofResponseStr(response));
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.socket = null;
            }
        }
    }


    private String createSuccessHtml() {

        return "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Title</title></head><body><h3>Login Success!</h3></body></html>";
    }

}
