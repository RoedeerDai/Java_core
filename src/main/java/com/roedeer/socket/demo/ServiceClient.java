package com.roedeer.socket.demo;

import java.io.*;
import java.net.Socket;

/**
 * Created by U6071369 on 7/23/2018.
 */
public class ServiceClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8899);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        PrintWriter pw = new PrintWriter(outputStream);
        pw.println("hello");
        pw.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String readLine = reader.readLine();
        System.out.println(readLine);

        inputStream.close();
        outputStream.close();
        socket.close();

    }
}
