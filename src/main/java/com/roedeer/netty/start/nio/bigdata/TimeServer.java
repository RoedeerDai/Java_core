package com.roedeer.netty.start.nio.bigdata;

/**
 * Created by U6071369 on 7/26/2018.
 */
public class TimeServer {

    /**
     * @author roedeer
     * @param args
     */
    public static void main(String[] args) {
        int port = 8088;
        if (args != null && args.length < 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }

}
