package com.roedeer.collection;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 线程安全的计数器
 * @Author Roedeer
 * @Date 1/6/2019 4:03 PM
 **/
public class Counter {
    private final AtomicInteger counter = new AtomicInteger();
    public void increase() {
        counter.incrementAndGet();
    }
}
