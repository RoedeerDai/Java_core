package com.roedeer.concurrent.syncTool;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Description 当Executor提交批处理任务时,并且希望它们完成后获得结果,如果用FutureTask,你可以循环获取task,
 *              并且用future.get()去获取结果,但是如果这个task没有完成,你就得阻塞在这里,实效性不高,
 *              无法获得其他完成任务的结果,而complete service可以得到一个结果获取一个,不用阻塞
 * @Author Roedeer
 * @Date 1/25/2019 10:33 AM
 **/
public class CompleteServiceTest {

    @Test
    public void NonCompleteService() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<String>[] futures = new FutureTask[10];

        /**
         * 产生一个随机数,模拟不同的任务的处理时间不同
         */
        for (int i = 0; i < 10; i++) {
            futures[i] = service.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    int rnt = new Random().nextInt(5);
                    try {
                        Thread.sleep(rnt * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("run rnt = " + rnt);
                    return String.valueOf(rnt * 1000);
                }
            });
        }

        /**
         * 获取结果时,如果任务没有完成,则阻塞,在顺序获取结果时,可能别的任务已经完成
         * 效率不高
         */
        for (int i = 0; i < futures.length; i++) {
            System.out.println(futures[i].get());
        }
        service.shutdown();
    }

    @Test
    public void CompleteServiceTest() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(10);

        CompletionService<String> completionService = new ExecutorCompletionService<String>(service);

        /**
         * 产生一个随机数,模拟不同的任务的处理时间不同
         */
        for (int i = 0; i < 10; i++) {
            completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    int rnt = new Random().nextInt(5);
                    try {
                        Thread.sleep(rnt * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("run rnt = " + rnt);
                    return String.valueOf(rnt * 1000);
                }
            });
        }

        /**
         * 获取结果时,总是先拿到队列上已经存在的对象,这样不用依次等待结果
         * 显然效率更高
         */
        for (int i = 0; i < 10; i++) {
            Future<String> future = completionService.take();
            System.out.println(future.get());
        }
        service.shutdown();

    }

}
