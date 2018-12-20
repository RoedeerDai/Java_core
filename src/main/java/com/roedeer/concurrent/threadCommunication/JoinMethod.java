package com.roedeer.concurrent.threadCommunication;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * @Description join的原理也是通过等待通知机制,源码通过调用的wait,join线程完成后会调用 notifyAll()方法,在JVM中实现,源码没有体现
 * @Author Roedeer
 * @Date 12/18/2018 11:24 AM
 **/
public class JoinMethod {
    public static void main(String[] args) {
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void join() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("running");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("running2");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        //等待线程1终止
        thread1.join();

        //等待线程2终止
        thread2.join();

        LOGGER.info("main over");
    }
}
