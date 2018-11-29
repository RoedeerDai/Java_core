package com.roedeer.socket.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by U6071369 on 7/23/2018.
 */
public class ServiceServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost",8899));

        while (true) {
            // 接受客户端的连接请求;accept是一个阻塞方法，会一直等待，到有客户端请求连接才返回
            Socket socket = server.accept();
            new Thread(new ServiceServerTask(socket)).start();
        }
    }
}
