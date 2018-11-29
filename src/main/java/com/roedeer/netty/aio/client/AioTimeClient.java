package com.roedeer.netty.aio.client;


/**
 * Created by U6071369 on 11/3/2018.
 */
public class AioTimeClient {
    public static void main(String[] args) {
        int port = 8888;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //default
            }
        }
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
        Exception e = new Exception("dingran");
        e.printStackTrace();
    }

}
