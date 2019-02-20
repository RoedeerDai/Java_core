package com.roedeer.concurrent.syncTool;

import java.util.concurrent.CountDownLatch;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 1/24/2019 9:05 PM
 **/
public class LatchTestHarness {

    public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);


        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();  //确保所有线程就绪之后开始执行
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long start = System.nanoTime();
        System.out.println("start time:" + start);
        startGate.countDown();
        endGate.await();  //等待所有线程执行减1操作完毕
        long end = System.nanoTime();
        System.out.println("end time:" + end);
        return end - start;
    }

    public static void main(String[] args) {
        Thread t = new Thread(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        try {
            long time = timeTasks(10,t);
            System.out.println(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
