package com.roedeer.concurrent.thread.testThread;


/**
 * Created by U6071369 on 7/18/2018.
 */
public class MySynchronized {
    public static void main(String[] args) {
        final MySynchronized mySynchronized = new MySynchronized();
        final MySynchronized mySynchronized1 = new MySynchronized();
        new Thread("thread1") {
            public void run() {
                synchronized (mySynchronized) {
                    try {
                        System.out.println(this.getName() + " start");
                        Thread.sleep(5000);
                        System.out.println(this.getName() + " 醒了");
                        System.out.println(this.getName() + " end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread("thread2") {
            public void run() {
                synchronized (mySynchronized) {
                    System.out.println(this.getName() + " 醒了");
                    System.out.println(this.getName() + " end");
                }
            }
        }.start();

    }
}
