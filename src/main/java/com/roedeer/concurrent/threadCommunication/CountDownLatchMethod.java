package com.roedeer.concurrent.threadCommunication;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 12/18/2018 4:21 PM
 **/
public class CountDownLatchMethod {

    private final static Logger LOGGER = LoggerFactory.getLogger(CountDownLatchMethod.class);

    public static void main(String[] args) throws Exception {
        countDownLatch();
    }

    /**
     * CountDownLatch 也是基于 AQS(AbstractQueuedSynchronizer) 实现的,具体参考 ReentrantLock
     * 步骤:
     * 1.初始化一个 CountDownLatch 时告诉并发的线程，然后在每个线程处理完毕之后调用 countDown() 方法
     * 2.该方法会将 AQS 内置的一个 state 状态 -1
     * 最终在主线程调用 await() 方法，它会阻塞直到 state == 0 的时候返回
     * @throws Exception
     */
    private static void countDownLatch() throws Exception{
        int thread = 3 ;
        long start = System.currentTimeMillis();
        final CountDownLatch countDown = new CountDownLatch(thread);
        for (int i= 0 ;i<thread ; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info("thread run");
                    try {
                        Thread.sleep(2000);
                        countDown.countDown();

                        LOGGER.info("thread end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        countDown.await();
        long stop = System.currentTimeMillis();
        LOGGER.info("main over total time={}",stop-start);
    }
}
