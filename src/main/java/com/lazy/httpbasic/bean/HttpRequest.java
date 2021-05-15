package com.lazy.httpbasic.bean;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    /**
     * 请求方法 GET/POST/PUT/DELETE/OPTION...
     */
    private String method;
    /**
     * 请求的uri
     */
    private String uri;
    /**
     * http版本
     */
    private String version;
    /**
     * 请求头
     */
    private Map<String, String> headers = new HashMap<>();
    /**
     * 请求参数
     */
    private Map<String, Object> parameters = new HashMap<>();
    /**
     * 请求参数相关
     */
    private String message;

    public String getHeader(String name) {
        return this.headers.get(name);
    }

    public void setHeader(String name, String val) {
        this.headers.put(name, val);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
