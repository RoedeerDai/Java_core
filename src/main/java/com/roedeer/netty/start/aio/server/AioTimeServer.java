package com.roedeer.netty.start.aio.server;


/**
 * Created by U6071369 on 11/3/2018.
 */
public class AioTimeServer {

    public static void main(String[] args) {
        int port = 8888;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //use default number
            }
        }
        //启动线程将异步时间服务器类处理器拉起
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
    }
}
