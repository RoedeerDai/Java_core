package com.roedeer.concurrent.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by U6071369 on 7/27/2018.
 */
public class ThreadCallable implements Callable<String> {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask task = new FutureTask(new ThreadCallable());
        new Thread(task).start();
        task.cancel(true);
        System.out.println(task.get());
    }



    @Override
    public String call() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(i);
        }
        return sb.toString();
    }
}
