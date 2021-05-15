package com.lazy.httpbasic.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {


    /**
     * 根据标准的http协议，解析请求行
     * 请求行，包含三个基本要素：请求方法 + URI + http版本，用空格进行分割，所以解析代码如下
     *
     * @param reader
     * @param request
     */
    private static void decodeRequestLine(BufferedReader reader, HttpRequest request) throws IOException {
        String[] strs = reader.readLine().split(" ");
        assert strs.length == 3;
        request.setMethod(strs[0]);
        request.setUri(strs[1]);
        request.setVersion(strs[2]);

        //解析参数
        String[] params = null;
        String[] uriAndParam = strs[1].split("\\?");
        if (uriAndParam.length == 2) {
            String param = uriAndParam[1];
            params = param.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] p = params[i].split("=");
                if (p.length == 2) {
                    request.getParameters().put(p[0], p[1]);
                }
            }
            request.setUri(uriAndParam[0]);
        }
    }

    /**
     * 根据标准http协议，解析请求头
     * 请求头的解析，从第二行，到第一个空白行之间的所有数据，都是请求头；请求头的格式也比较清晰， 形如 key:value
     *
     * @param reader
     * @param request
     * @throws IOException
     */
    private static void decodeRequestHeader(BufferedReader reader, HttpRequest request) throws IOException {
        Map<String, String> headers = new HashMap<>(16);
        String line = reader.readLine();
        String[] kv;
        while (!"".equals(line)) {
            kv = line.split(":");
            assert kv.length == 2;
            headers.put(kv[0].trim(), kv[1].replaceAll("\"", "").trim());
            line = reader.readLine();
        }
        request.setHeaders(headers);
    }

    /**
     * 根据标注http协议，解析正文
     *
     * @param reader
     * @param request
     * @throws IOException
     */
    private static void decodeRequestMessage(BufferedReader reader, HttpRequest request) throws IOException {
        int contentLen = Integer.parseInt(request.getHeaders().getOrDefault("Content-Length", "0"));
        if (contentLen == 0) {
            // 表示没有message，直接返回
            // 如get/options请求就没有message
            return;
        }
        char[] message = new char[contentLen];
        reader.read(message);
        request.setMessage(new String(message));
    }

    /**
     * http的请求可以分为三部分
     * <p>
     * 第一行为请求行: 即 方法 + URI + 版本
     * 第二部分到一个空行为止，表示请求头
     * 空行
     * 第三部分为接下来所有的，表示发送的内容,message-body；其长度由请求头中的 Content-Length 决定
     * <p>
     * 几个实例如下
     *
     * @param reqStream
     * @return
     */
    public static HttpRequest parse2request(InputStream reqStream) throws IOException {
        BufferedReader httpReader = new BufferedReader(new InputStreamReader(reqStream, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest();
        decodeRequestLine(httpReader, httpRequest);
        decodeRequestHeader(httpReader, httpRequest);
        decodeRequestMessage(httpReader, httpRequest);
        return httpRequest;
    }

}
