package com.roedeer.socket.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by U6071369 on 8/11/2018.
 */
public class ChatServer {

    private static class Send implements Runnable {

        private Socket socket;

        public Send(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            PrintWriter printWriter = null;
            Scanner scanner = null;
            try {
                printWriter = new PrintWriter(socket.getOutputStream());
                scanner = new Scanner(System.in);
                while (true) {
                    String s = scanner.nextLine();
                    printWriter.write(s + "\n");
                    printWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                scanner.close();
                printWriter.close();
            }
        }
    }

    private static class Receive implements Runnable {

        private Socket socket;

        public Receive(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            Scanner scanner = null;
            try {
                scanner = new Scanner(socket.getInputStream());
                while (scanner.hasNext()) {
                    System.out.println(scanner.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                scanner.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new Send(socket)).start();
            new Thread(new Receive(socket)).start();
        }
    }

}
