package com.roedeer.netty.nio;

/**
 * Created by U6071369 on 10/26/2018.
 */
public class NioTimeClient {
    public static void main(String[] args) {
        int port = 8888;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //default
            }
        }
        new Thread(new NioTimeClientHandle("127.0.0.1", port), "NioTimeClient-001").start();
    }
}
