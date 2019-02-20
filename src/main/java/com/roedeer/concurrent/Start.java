package com.roedeer.concurrent;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 1/24/2019 10:26 AM
 **/
public class Start {

    /**
     * 高并发   线程安全   并发包
     * 1.各种并发容器,比如ConcurrentHashMap、CopyOnWriteArrayList
     * 2.各种线程安全队列(Queue/Deque),如ArrayBlockingQueue，SynchronousQueue
     */

    /**
     * ConcurrentHashMap早期主要是分离锁,将内部进行分段(Segment),默认16,segment是基于ReentrantLock的扩展实现
     * 里面则是HashEntry的数组,和hashmap类似,HashEntry内部使用volatile的value字段来保证可见性
     * put二次哈希,重复扫描,检测冲突,segment单独扩容
     * 分离锁副作用: size方法因为并发put导致不准确,通过重试机制(2次)来试图获得可靠值,如果没有监控到变化(通过对比Segment.modCount)
     * 就直接返回,否则获取锁进行操作
     * 分离锁还限制了map的初始化
     *
     * JDK1.8变化
     * 结构变成和HashMap类似,同样是大的桶(bucket)数组,然后内部也是一个个所谓的链表结构(bin)
     * 内部仍然有segment定义,但仅仅是为了保证序列化时的兼容性而已,不再有任何结构上的用处
     * 不在使用segment,初始化操作大大简化,修改为lazy-load模式,避免初始开销
     * 数据存储使用volatile来保证可见性
     * 使用CAS等操作,在特定场景使用无锁并发操作
     * 使用Unsafe，LongAdder之类底层手段,进行极端情况的优化
     */

    /**
     * CopyOnWriteArrayList用来替代同步List,提供更好的并发性能
     * 写入时复制(Copy-On-Write)
     *
     * java5开始增加
     * Queue:  queue上的操作不会阻塞
     *      ConcurrentLinkedQueue:先进先出队列
     *      PriorityQueue:非并发的优先队列
     * BlockilongQueue:对Queue的扩展,增加了可阻塞的插入和获取等操作,适用于生产消费者模式
     *      LinkedBckingQueue和ArrayBlockingQueue是FIFO队列
     *      PriorityBlockingQueue
     *
     * java6开始增加,对Queue和BlockingQueue的扩展
     * Deque:双端队列  ArrayDeque  LinkedBlockingDeque,
     *       适合于工作密取(Work Stealing),空闲时从其他双端队列末尾秘密的获取工作
     * BlockingDeque:
     */

    public static void main(String[] args) {
        System.out.println(System.getProperty("user"));
    }



}
