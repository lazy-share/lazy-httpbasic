package com.lazy.httpbasic.conf;

public class Config {

    /**
     * 控制台会话超时时间，单位毫秒, 默认10分钟
     */
    public static final long CONSOLE_HTTP_SESSION_TIME = 600000;
    /**
     * 心跳控制台监听默认端口
     */
    public static final int HEARTBEAT_CONSOLE_PORT = 9550;
    /**
     * 控制台HTTP BASIC 用户名
     */
    public static final String CONSOLE_HTTP_BASIC_USERNAME = "root";
    /**
     * 控制台HTTP BASIC 密码
     */
    public static final String CONSOLE_HTTP_BASIC_PASSWORD = "123456";
    /**
     * realm
     */
    public static final String REALM = "HttpBasicServer";


}
