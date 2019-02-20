package com.roedeer.concurrent.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by U6071369 on 7/27/2018.
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Long start = System.currentTimeMillis();
        final Random random = new Random();
        final List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    list.add(random.nextInt());
                }
            };
            thread.start();
            thread.join();
        }
        System.out.println("时间：" + (System.currentTimeMillis() - start));
        System.out.println("size:" + list.size());
    }
}
