package com.roedeer.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Roedeer on 2018/6/17.
 */
public class MyClient {

    public void go() {
        System.out.println("正在向服务器请求连接......");
        Socket socket = null;
        Scanner keyboardscanner = null;
        Scanner inscanner = null;
        PrintWriter printWriter = null;
        try {
            socket = new Socket("127.0.0.1", 5018);
            inscanner = new Scanner(socket.getInputStream());
            System.out.println(inscanner.nextLine());
            printWriter = new PrintWriter(socket.getOutputStream());
            System.out.print("我(客户端)：");
            //先读取键盘录入方可向服务端发送消息
            keyboardscanner = new Scanner(System.in);
            while(keyboardscanner.hasNextLine()){
                String keyborddata = keyboardscanner.nextLine();
                //展示到己方的控制台
//                System.out.println("我(客户端)："+keyborddata);
                //写到服务端的的控制台
                printWriter.println(keyborddata);
                printWriter.flush();
                //阻塞等待接收服务端的消息
                String indata = inscanner.nextLine();
                System.out.println("服务端："+indata);
                System.out.print("我(客户端)：");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            keyboardscanner.close();
            inscanner.close();
            printWriter.close();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyClient myClient = new MyClient();
        myClient.go();
    }
}
