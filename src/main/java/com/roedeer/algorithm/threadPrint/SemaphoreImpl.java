package com.roedeer.algorithm.threadPrint;


import java.util.concurrent.Semaphore;

public class SemaphoreImpl extends Thread {

    private static Semaphore sa = new Semaphore(1);
    private static Semaphore sb = new Semaphore(0);
    private static Semaphore sc = new Semaphore(0);

    private static void controlPrint(String name) {
        if ("A".equals(name)) {
            print("A", sa, sb);
        } else if ("B".equals(name)) {
            print("B", sb, sc);
        } else if ("C".equals(name)) {
            print("C", sc, sa);
        }
    }

    private static void print(String name, Semaphore semaphore, Semaphore nextSemaphore) {
        try {
            for (int i = 0; i < 10; i++) {
                semaphore.acquire();
                System.out.println(name);
                nextSemaphore.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                controlPrint("A");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                controlPrint("B");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                controlPrint("C");
            }
        }).start();
    }


}
