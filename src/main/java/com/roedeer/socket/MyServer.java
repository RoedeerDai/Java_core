package com.roedeer.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Roedeer on 2018/6/24.
 */
public class MyServer {

    public void go() {
        System.out.println("等待客户端连接......");
        PrintWriter printWriter = null;
        Scanner keyboardscanner = null;
        Scanner inScanner = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5018);
            Socket socket = serverSocket.accept();
            System.out.println(socket.getInetAddress()+"已经成功连接到此台服务器上");
            printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("已成功连接到远程服务器！"+"\t"+"请您先发言");
            printWriter.flush();
            keyboardscanner = new Scanner(System.in);
            inScanner = new Scanner(socket.getInputStream());
            //阻塞等待客户端发送消息过来
            while (inScanner.hasNextLine()) {
                String indata = inScanner.nextLine();
                System.out.println("客户端："+indata);
                System.out.print("我(服务端):");
                String keyboarddata = keyboardscanner.nextLine();
//                System.out.println("我(服务端)：" + keyboarddata);
                printWriter.println(keyboarddata);
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
            keyboardscanner.close();
            inScanner.close();
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyServer myServer = new MyServer();
        myServer.go();
    }

}
