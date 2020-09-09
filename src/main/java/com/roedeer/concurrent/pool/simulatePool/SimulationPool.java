package com.roedeer.concurrent.pool.simulatePool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @Description 模拟线程池
 * @Author Roedeer
 * @Date 5/31/2019 10:27 AM
 **/
public class SimulationPool {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimulationPool.class);
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 最小线程数,也称核心线程数
     */
    private volatile int minSize;

    /**
     * 最大线程数
     */
    private volatile int maxSize;

    /**
     * 线程需要被回收的时间,保活时间
     */
    private long keepAliveTime;
    private TimeUnit unit;

    /**
     * 存放线程的阻塞队列
     */
    private BlockingQueue<Runnable> workQueue;

    /**
     * 存放线程池
     */
    private volatile Set<Worker> workers;

    /**
     * 是否关闭线程池标志
     */
    private AtomicBoolean isShutDown = new AtomicBoolean(false);

    /**
     * 能提交到线程池中的任务总数
     */
    private AtomicInteger totalTask = new AtomicInteger();

    /**
     * 线程池任务全部执行完毕后的通知组件
     */
    private Object shutDownNotify = new Object();

    private Notify notify;

    /**
     * @param minSize       最小线程数
     * @param maxSize       最大线程数
     * @param keepAliveTime 线程保活时间
     * @param unit          时间单位
     * @param workQueue     阻塞队列
     * @param notify        通知接口
     */
    public SimulationPool(int minSize, int maxSize, long keepAliveTime,
                          TimeUnit unit, BlockingQueue<Runnable> workQueue, Notify notify) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.workQueue = workQueue;
        this.notify = notify;

        workers = new ConcurrentHashSet();
    }

    /**
     * 执行任务
     * @param runnable  需要执行的任务
     */
    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable nullPointerException");
        }
        if (isShutDown.get()) {
            LOGGER.info("线程池已经关闭,不能再提交任务");
            return;
        }
        //提交的线程  计数
        totalTask.incrementAndGet();

        //小于最小线程数时创建新线程
        if (workers.size() < minSize) {
            addWorker(runnable);
            return;
        }

        boolean offer = workQueue.offer(runnable);
        //写入队列失败
        if (!offer) {
            //创建新的线程执行
            if (workers.size() < maxSize) {
                addWorker(runnable);
                return;
            } else {
                LOGGER.info("超过最大线程数");
                try {
                    //会阻塞写入队列,JUC会在这里执行拒绝策略
                    workQueue.put(runnable);
                } catch (InterruptedException e) {

                }
            }
        }
    }


    private void addWorker(Runnable runnable) {
        Worker worker = new Worker(runnable, true);
        worker.startTask();
        workers.add(worker);
    }

    /**
     * 工作线程
     */
    private final class Worker extends Thread {

        private Runnable task;

        private Thread thread;

        /**
         * true --> 创建新的线程执行
         * false --> 从队列里获取线程执行
         */
        private boolean isNewTask;

        public Worker(Runnable task, boolean isNewTask) {
            this.task = task;
            this.isNewTask = isNewTask;
            thread = this;
        }

        public void startTask() {
            thread.start();
        }

        public void close() {
            thread.interrupt();
        }

        @Override
        public void run() {
            Runnable task = null;
            if (isNewTask) {
                task = this.task;
            }
            try {
                while ((task != null || (task = getTask()) != null)) {
                    try {
                        //执行任务
                        task.run();
                    } finally {
                        //任务执行完毕
                        task = null;
                        int number = totalTask.decrementAndGet();
                        if (number == 0) {
                            synchronized (shutDownNotify) {
                                shutDownNotify.notify();
                            }
                        }
                    }
                }
            } finally {
                //释放线程
                boolean remove = workers.remove(this);
                tryClose(true);
            }
        }
    }

    /**
     * 从队列中获取任务
     * @return
     */
    private Runnable getTask() {
        //关闭标识及任务是否全部完成
        if (isShutDown.get() && totalTask.get() == 0) {
            return null;
        }
        lock.lock();
        try {
            Runnable task = null;
            if (workers.size() > minSize) {
                //大于核心线程数时需要用保活时间获取任务
                task = workQueue.poll(keepAliveTime, unit);
            } else {
                task = workQueue.take();
            }

            if (task != null) {
                return task;
            }
        } catch (InterruptedException e) {
            return null;
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 执行任务完毕后关闭线程池
     */
    public void shutdown() {
        isShutDown.set(true);
        tryClose(true);
    }

    /**
     * 立即关闭线程池,会造成任务丢失
     */
    public void shutdownNow() {
        isShutDown.set(true);
        tryClose(false);
    }

    /**
     * 阻塞等待任务执行完毕
     */
    public void mainNotify() {
        synchronized (shutDownNotify) {
            while (totalTask.get() > 0) {
                try {
                    shutDownNotify.wait();
                    if (notify != null) {
                        notify.notifyListen();
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    /**
     * 关闭线程池
     * @param isTry true 尝试关闭   -->会等待所有任务执行完毕
     *              false 立即关闭  -->任务有丢失的可能
     */
    private void tryClose(boolean isTry) {
        if (!isTry) {
            closeAllTask();
        } else {
            if (isShutDown.get() && totalTask.get() == 0) {
                closeAllTask();
            }
        }
    }

    /**
     * 关闭所有任务
     */
    private void closeAllTask() {
        for (Worker worker : workers) {
            worker.close();
        }
    }

    /**
     * 获取工作线程数量
     * @return
     */
    public int getWorkerCount() {
        return workers.size();
    }

    /**
     * 内部存放工作线程容器
     *
     * Worker是最终存放线程池中运行的线程,在JUC源码中是一个HashSet,所有对他的操作都需要加锁
     * 这里自己定义了一个线程安全的Set称为ConcurrentHashSet
     * 和HashSet类似也是借助于HashMap类存放数据,利用其key不可重复的特性来实现Set
     * 只是这里的HashMap是用线程安全的ConcurrentHashMap来实现的,这样能保证对他的写入,删除都是线程安全的
     * ConcurrentHashMap的size()函数并不准确,单独利用AtomicInteger来统计容器大小
     *
     * @param <T>
     */
    private final class ConcurrentHashSet<T> extends AbstractSet<T> {

        private ConcurrentHashMap<T, Object> map = new ConcurrentHashMap<>();
        private final Object PRESENT = new Object();

        private AtomicInteger count = new AtomicInteger();


        @Override
        public Iterator<T> iterator() {
            return map.keySet().iterator();
        }

        @Override
        public boolean add(T t) {
            count.incrementAndGet();
            return map.put(t, PRESENT) == null;
        }

        @Override
        public boolean remove(Object o) {
            count.decrementAndGet();
            return map.remove(o) == PRESENT;
        }

        @Override
        public int size() {
            return count.get();
        }
    }

}
