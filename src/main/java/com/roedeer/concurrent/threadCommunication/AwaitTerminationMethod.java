package com.roedeer.concurrent.threadCommunication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * 如果是用线程池来管理线程，可以使用awaitTermination方式来让主线程等待线程池中所有任务执行完毕
 * 使用awaitTermination方法需要提前关闭线程池,如调用shutdown方法
 * 调用shutdown方法之后线程池会停止接受新的任务,并且会平滑的关闭线程池中现有的任务
 *
 * @Author Roedeer
 * @Date 12/18/2018 4:34 PM
 **/
public class AwaitTerminationMethod {

    private static final Logger logger = LoggerFactory.getLogger(AwaitTerminationMethod.class);

    private static void executorService() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(10);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,5,1, TimeUnit.MILLISECONDS, queue);
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                logger.info("running");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                logger.info("running2");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        poolExecutor.shutdown();
        while (!poolExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
            logger.info("线程还在执行......");
        }
        logger.info("main over");

    }

    public static void main(String[] args) throws InterruptedException {
        executorService();
    }

}
