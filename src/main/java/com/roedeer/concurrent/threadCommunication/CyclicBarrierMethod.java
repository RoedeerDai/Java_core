package com.roedeer.concurrent.threadCommunication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 12/18/2018 4:23 PM
 **/
public class CyclicBarrierMethod {

    private static final Logger LOGGER = LoggerFactory.getLogger(CyclicBarrierMethod.class);

    public static void main(String[] args) {
        cycliBarrier();
    }

    /**
     * CyclingBarrier并发工具,中文名叫屏障或栅栏,也可以用于线程间通信
     * 它可以等待 N 个线程都达到某个状态后继续运行的效果
     * 1.首先初始化线程参与者
     * 2.调用 await() 将会在所有参与者线程都调用之前等待
     * 3.直到所有参与者都调用了 await() 后，所有线程从 await() 返回继续后续逻辑
     */
    private static void cycliBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("------------>thread1 run");
                try {
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LOGGER.info("------------>thread1 end do something");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("------------>thread2 run");
                try {
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LOGGER.info("------------>2thread end do something");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("------------->thread3 run");
                try {
                    Thread.sleep(5000);
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LOGGER.info("-------------->thread3 end do something");
            }
        }).start();

        LOGGER.info("=============main thread");
    }
}
