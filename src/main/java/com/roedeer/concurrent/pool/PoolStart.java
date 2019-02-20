package com.roedeer.concurrent.pool;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 1/25/2019 9:35 PM
 **/
public class PoolStart {

    /**
     * 限定线程等待时间,线程池大小,创建和销毁,有界队列和无界队列
     * 工作队列的饱和策略四种
     */
    @Test
    public void testDemo() {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        int cpuNums = Runtime.getRuntime().availableProcessors();
        System.out.println(cpuNums);
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(cpuNums);
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(8);

        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        /**
         * 创建一个固定大小的线程池,并采用有界队列以及"调用者运行"饱和策略
         */
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(10, 10,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<Runnable>(10));
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

}
