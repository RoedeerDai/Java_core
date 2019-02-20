package com.roedeer.collection;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * @Description 改成基本类型long的线程安全计数器
 * @Author Roedeer
 * @Date 1/6/2019 4:04 PM
 **/
public class CompactCounter {
    private volatile long counter;
    private static final AtomicLongFieldUpdater<CompactCounter> updater =
            AtomicLongFieldUpdater.newUpdater(CompactCounter.class, "counter");
    public void increase() {
        updater.incrementAndGet(this);
    }
}
