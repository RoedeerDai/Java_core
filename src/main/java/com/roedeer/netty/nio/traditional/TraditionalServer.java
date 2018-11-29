package com.roedeer.netty.nio.traditional;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by U6071369 on 7/26/2018.
 */
public class TraditionalServer {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(2000);
        System.out.println("等待, 端口为:" + serverSocket.getLocalPort());

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("新连接:  " + socket.getInetAddress() + ":" + socket.getPort());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[4096];
            while (true) {
                int nread = inputStream.read(bytes, 0, 4096);
                System.out.println(new String(bytes, "UTF-8"));
                if (-1 == nread) {
                    break;
                }
            }
            socket.close();
            System.out.println("Connection closed by client");
        }

    }

}
