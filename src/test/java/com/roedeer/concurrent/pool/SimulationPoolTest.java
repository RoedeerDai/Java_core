package com.roedeer.concurrent.pool;

import com.roedeer.concurrent.pool.simulatePool.Notify;
import com.roedeer.concurrent.pool.simulatePool.SimulationPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 5/31/2019 3:34 PM
 **/
public class SimulationPoolTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimulationPool.class);


    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue = new ArrayBlockingQueue(10);
        SimulationPool pool = new SimulationPool(3, 5, 1, TimeUnit.SECONDS, queue, new Notify() {
            @Override
            public void notifyListen() {
                LOGGER.info("任务执行完毕");
            }
        });
        for (int i = 0; i < 10; i++) {
            pool.execute(new Worker(i));
        }


        LOGGER.info("=======休眠前线程池活跃线程数={}======",pool.getWorkerCount());

        TimeUnit.SECONDS.sleep(5);
        LOGGER.info("=======休眠后线程池活跃线程数={}======",pool.getWorkerCount());

        for (int i = 0; i < 3; i++) {
            pool.execute(new Worker(i + 100));
        }

        pool.shutdown();
//        pool.shutdownNow();
        //pool.execute(new Worker(100));
        LOGGER.info("++++++++++++++");
        pool.mainNotify();
    }

    private static class Worker implements Runnable {

        private int state;

        public Worker(int state) {
            this.state = state;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                LOGGER.info("state={}",state);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
