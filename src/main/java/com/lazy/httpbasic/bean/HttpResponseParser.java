package com.lazy.httpbasic.bean;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseParser {

    public static String buildResponse(HttpRequest request, String response) {
        HttpResponse httpResponse = ofResponse(request, response);
        StringBuilder builder = new StringBuilder();
        buildResponseLine(httpResponse, builder);
        buildResponseHeaders(httpResponse, builder);
        buildResponseMessage(httpResponse, builder);
        return builder.toString();
    }

    public static String ofResponseStr(HttpResponse httpResponse) {
        StringBuilder builder = new StringBuilder();
        buildResponseLine(httpResponse, builder);
        buildResponseHeaders(httpResponse, builder);
        buildResponseMessage(httpResponse, builder);
        return builder.toString();
    }

    public static HttpResponse ofResponse(HttpRequest request, String response) {
        if (response == null) {
            response = "";
        }
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(200);
        httpResponse.setStatus("ok");
        httpResponse.setVersion(request.getVersion());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        headers.put("Cache-Control", "no-store");
        headers.put("Content-Length", String.valueOf(response.getBytes().length));
        httpResponse.setHeaders(headers);
        httpResponse.setMessage(response);
        return httpResponse;
    }

    private static void buildResponseLine(HttpResponse response, StringBuilder stringBuilder) {
        stringBuilder.append(response.getVersion()).append(" ").append(response.getCode()).append(" ")
                .append(response.getStatus()).append("\n");
    }

    private static void buildResponseHeaders(HttpResponse response, StringBuilder stringBuilder) {
        for (Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        stringBuilder.append("\n");
    }

    private static void buildResponseMessage(HttpResponse response, StringBuilder stringBuilder) {
        stringBuilder.append(response.getMessage());
    }

}
