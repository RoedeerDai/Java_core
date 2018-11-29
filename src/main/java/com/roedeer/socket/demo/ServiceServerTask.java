package com.roedeer.socket.demo;

import java.io.*;
import java.net.Socket;

/**
 * Created by U6071369 on 7/23/2018.
 */
public class ServiceServerTask implements Runnable {

    Socket socket;
    InputStream inputStream;
    OutputStream outputStream;


    public ServiceServerTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();


            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            System.out.println(line);

            GetDataServiceImpl getDataServiceImpl = new GetDataServiceImpl();
            String s = getDataServiceImpl.getData(line);

            PrintWriter pw = new PrintWriter(outputStream);
            pw.println(s);
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
