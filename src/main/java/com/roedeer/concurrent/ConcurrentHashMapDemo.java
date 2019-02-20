package com.roedeer.concurrent;

import com.google.common.util.concurrent.AtomicLongMap;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Description 统计每个接口访问的次数。一个接口对应一个url，也就是一个字符串，每调用一次对其进行加一处理
 *              CAS compare and swap
 * @Author Roedeer
 * @Date 1/24/2019 10:58 AM
 **/
public class ConcurrentHashMapDemo {

    private final Map<String, Long> urlCounter = new ConcurrentHashMap<>();
    private AtomicLongMap<String> urlCount3 = AtomicLongMap.create();

    //call interface and count increase one
    public long increase(String url) {
        Long oldValue = urlCounter.get(url);
        Long newValue = (oldValue == null) ? 1L : oldValue + 1;
        urlCounter.put(url, newValue);
        return newValue;
    }

    /**
     * replace method : CAS操作,保证比较和设置是一个原子操作
     * thread A increase, 旧值被修改的话就会导致replace失败,没有被修改的话就直接put newValue
     * 使用一个循环,不断获取oldValue知道replace成功,才退出循环
     *
     * 初始化使用putIfAbsent方法,也是CAS操作,若没有,则添加进行初始化,失败的话就进行+1操作
     * @param url
     * @return
     */
    public long increase2(String url) {
        Long oldValue, newValue;
        while (true) {
            oldValue = urlCounter.get(url);
            if (oldValue == null) {
                newValue = 1L;
                //初始化成功,退出循环
                if (urlCounter.putIfAbsent(url, 1L) == null)
                    break;
                //如果初始化失败,说明其他线程已经初始化过了
            } else {
                newValue = oldValue + 1;
                // +1 success, exit loop
                if (urlCounter.replace(url, oldValue, newValue))
                    break;
                // +1 fail, other thread have modify oldValue
            }
        }
        return newValue;
    }

    public long increase3(String url) {
        long newValue = urlCount3.incrementAndGet(url);
        return newValue;
    }

    //get call count
    public Long getCount(String url) {
        return urlCounter.get(url);
    }

    public Long getCount3(String url) {
        return urlCount3.get(url);
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final ConcurrentHashMapDemo demo = new ConcurrentHashMapDemo();
        int callTime = 100000;
        final String url = "http://localhost:8080/hello";
        CountDownLatch countDownLatch = new CountDownLatch(callTime);
        //模拟并发情况下的接口调用统计
        for (int i = 0; i < callTime; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    demo.increase2(url);
                    demo.increase3(url);
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        //等待所有线程统计完成后输出调用次数
        System.out.println("call count : " + demo.getCount(url));
        System.out.println("call count : " + demo.getCount3(url));

        /**
         * console output:
         * call count : 98291
         * 结果并没有显示加同步,问题出在increase方法上,如果对increase方法加锁,那么开销很大
         */
    }

}
