package com.roedeer.concurrent.thread;
/**
 * Created by U6071369 on 7/17/2018.
 */
public class MyThreadWithImpliment implements Runnable {

    int x;

    public MyThreadWithImpliment(int x) {
        this.x = x;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("线程" + name + "的run方法被调用");
        for (int i = 0; i < 10; i++) {
            System.out.println(x);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyThreadWithImpliment(1));
        Thread thread2 = new Thread(new MyThreadWithImpliment(2));
        thread1.start();
        thread2.start();
    }

}
