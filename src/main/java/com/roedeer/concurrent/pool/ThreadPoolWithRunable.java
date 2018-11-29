package com.roedeer.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by U6071369 on 7/19/2018.
 */
public class ThreadPoolWithRunable {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i = 1; i < 5; i++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread name: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        pool.shutdown();
    }
}
