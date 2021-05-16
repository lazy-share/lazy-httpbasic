package com.lazy.httpbasic;

import com.lazy.httpbasic.conf.Config;
import com.lazy.httpbasic.task.WorkTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Bootstrap {

    private static final int nThreads = Runtime.getRuntime().availableProcessors();

    /**
     * 主线程池
     */
    private static final ExecutorService bootstrapExecutor = Executors.newFixedThreadPool(2);
    /**
     * 任务线程池
     */
    private static final ExecutorService taskExecutor = new ThreadPoolExecutor(
            nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(100),
            new ThreadPoolExecutor.DiscardPolicy());

    /**
     * 启动方法
     *
     * @param args
     */
    public static void main(String[] args) {

        //启动socket线程
        bootstrapExecutor.submit(new Startup());
    }


    /**
     * 启动线程
     */
    static class Startup implements Runnable {


        @Override
        public void run() {

            ServerSocket serverSocket = null;
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(Config.HEARTBEAT_CONSOLE_PORT);
                System.out.println("The Http-Basic Server is start in port:" + Config.HEARTBEAT_CONSOLE_PORT);
                while (true) {
                    socket = serverSocket.accept();
                    //接受客户端请求后提交给任务线程池去执行
                    taskExecutor.submit(new WorkTask(socket));
                }
            } catch (Exception ignored) {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        //ignored
                    }
                }
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        //ignored
                    }
                }
            }
        }
    }
}
