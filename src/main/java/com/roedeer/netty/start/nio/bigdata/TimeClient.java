package com.roedeer.netty.start.nio.bigdata;

/**
 * Created by U6071369 on 7/26/2018.
 */
public class TimeClient {
    public static void main(String[] args) {

        int port = 8088;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
    }
}
