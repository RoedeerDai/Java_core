package com.roedeer.algorithm.threadPrint;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC {

    private static int cnt = 0;
    private static final int count = 30;

    public static void main(String[] args) {

        final Lock lock = new ReentrantLock();
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    if (cnt >= count) {
                        lock.unlock();
                        return;
                    }
                    if (cnt%3 == 0) {
                        System.out.println("A");
                        cnt++;
                    }
                    lock.unlock();
                }
            }
        });
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    if (cnt >= count) {
                        lock.unlock();
                        return;
                    }
                    if (cnt%3 == 1) {
                        System.out.println("B");
                        cnt++;
                    }
                    lock.unlock();
                }
            }
        });
        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    if (cnt >= count) {
                        lock.unlock();
                        return;
                    }
                    if (cnt%3 == 2) {
                        System.out.println("C");
                        cnt++;
                    }
                    lock.unlock();
                }
            }
        });
        a.start();
        b.start();
        c.start();


    }

}
