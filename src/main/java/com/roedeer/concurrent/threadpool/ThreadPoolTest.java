package com.roedeer.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by U6071369 on 7/27/2018.
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        Long start = System.currentTimeMillis();
        final Random random = new Random();
        final List<Integer> list = new ArrayList<>();


//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1) //有界队列
//                new LinkedBlockingQueue<Runnable>() //无界队列
//                new RejectedExecutionHandler() { //定义自己的reject
//                    @Override
//                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                        System.out.println("当前拒绝任务：" + r.toString());
//                    }
//                }

        ){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("线程执行之前");
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("线程执行之后");
            }
        };


        for (int i = 0; i < 100000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    list.add(random.nextInt());
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("时间：" + (System.currentTimeMillis() - start));
        System.out.println("size:" + list.size());
    }
}
