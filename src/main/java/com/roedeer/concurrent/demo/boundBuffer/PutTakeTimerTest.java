package com.roedeer.concurrent.demo.boundBuffer;


import org.junit.Assert;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 2/24/2019 9:48 AM
 **/
public class PutTakeTimerTest {
    private static final ExecutorService pool
            = Executors.newCachedThreadPool();
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final BarrierTimer timer;
    private final CyclicBarrier barrier;
    private final BoundBuffer<Integer> bb;
    private final int nTrials, nPairs;

    public static void main(String[] args) throws InterruptedException {
        int tpt = 100000; //每个线程中的测试次数
        for (int cap = 1; cap <= 1000; cap *= 10) {
            System.out.println("Capacity: " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) {
                PutTakeTimerTest t = new PutTakeTimerTest(cap, pairs, tpt);
                System.out.println("Pairs: " + pairs);
                t.test();
                Thread.sleep(1000);
                t.test();
                Thread.sleep(1000);
            }
        }
        pool.shutdown();
    }

    PutTakeTimerTest(int capacity, int nPairs, int nTrials) {
        this.bb = new BoundBuffer<Integer>(capacity);
        this.nTrials = nTrials;
        this.nPairs = nPairs;
        this.timer = new BarrierTimer();
        this.barrier = new CyclicBarrier(nPairs * 2 + 1, timer);
    }

    void test() {
        try {
            timer.clear();
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            barrier.await();
            barrier.await();
            long nsPerItem = timer.getTime() / (nPairs * (long)nTrials);
            System.out.println("Throughput: " + nsPerItem + " ns/item");
            Assert.assertEquals(putSum.get(), takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class BarrierTimer implements Runnable {

        private boolean started;
        private long startTime, endTime;

        @Override
        public synchronized void run() {
            long t = System.nanoTime();
            if (!started) {
                started = true;
                startTime = t;
            } else {
                endTime = t;
            }
        }

        public synchronized void clear() {
            started = false;
        }

        public synchronized long getTime() {
            return endTime - startTime;
        }
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    class Producer implements Runnable {
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int)System.nanoTime());
                int sum = 0;
                barrier.await();
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
