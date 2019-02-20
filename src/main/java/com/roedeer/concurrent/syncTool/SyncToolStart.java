package com.roedeer.concurrent.syncTool;

/**
 * @Description
 * @Author Roedeer
 * @Date 1/24/2019 8:59 PM
 **/
public class SyncToolStart {

    /**
     * 同步工具类可以是任何一个对象,包括阻塞队列(Deque),信号量(Semaphore),栅栏(Barrier)以及闭锁(Latch)
     * 他们封装了一些状态,这些状态将决定执行同步工具类的线程是继续执行还是等待
     * 主要应用：
         * 确保所有资源都被初始化后才执行
         * 确保某个服务在其依赖的所有其他服务都已经启动之后才启动
         * 等待直到某个操作的所有参与者都就绪再继续执行
     *
     * 延迟和周期任务
     * Timer表现并不好,线程泄露问题
     * DelayQueue:实现了BlockingQueue,并为ScheduledThreadPoolExecutor提供调度功能
     */

}
