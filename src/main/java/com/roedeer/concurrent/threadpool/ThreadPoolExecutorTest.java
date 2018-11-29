package com.roedeer.concurrent.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by U6071369 on 7/28/2018.
 * 执行submit和execute的区别,两个方法返回值也不一样
 * submit    java.util.concurrent.FutureTask线程执行之前
 * execute   com.roedeer.threadpool.ThreadPoolExecutorTest线程执行之前
 *
 */
public class ThreadPoolExecutorTest implements Runnable {

    AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void run() {
        int task = atomicInteger.incrementAndGet();
        System.out.println("taskId = " + task);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>()
        ){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(r.getClass().getName() + "线程执行之前");
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("线程执行之后");
            }
        };

        ThreadPoolExecutorTest test = new ThreadPoolExecutorTest();

        for (int i = 0; i < 2; i++) {
//            poolExecutor.submit(test);
            poolExecutor.execute(test);
        }
        poolExecutor.shutdown();
    }


}
