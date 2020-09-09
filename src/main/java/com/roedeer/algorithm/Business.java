package com.roedeer.algorithm;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Business {

    Lock lock = new ReentrantLock();
    Condition first = lock.newCondition();
    Condition second = lock.newCondition();
    Condition third = lock.newCondition();  // 三个任务,按一定顺序执行

    int calling = 1;

    public void main(int i) {
        lock.lock();    //上锁,执行完整个main

        try {
            while (calling != 1) {
                try {
                    first.await();
                } catch (InterruptedException e) {

                }
            }
            for (int j = 0; j < 3; j++) {
                System.out.println("first thread sequence of " + j + ", loop of " + i);
            }
            calling = 2;    //任务1跑完,跑任务2
            second.signal();    //任务1唤醒任务2
        } finally {
            lock.unlock();
        }
    }


}
