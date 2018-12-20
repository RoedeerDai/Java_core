package com.roedeer.concurrent.volatileTest;

/**
 * @Description TODO
 * volatile防止指令重排,经典应用就是双重懒加载的单例模式
 *
 * @Author Roedeer
 * @Date 12/18/2018 5:52 PM
 **/
public class VolatileSingleton {

    private static volatile VolatileSingleton singleton;

    private VolatileSingleton() {

    }

    /**
     * singleton = new VolatileSingleton()分为三步
     * 1.分配内存空间
     * 2.初始化对象
     * 3.将singleton对象指向分配的内存地址
     * 加上volatile是为了让以上的三步操作顺序执行,
     * 反之有可能在第二步在第三步之前被执行之前就有可能让某个线程拿到的单例对象是还没有初始化的,以致于报错
     *
     * Atomic包中的value AbstractQueuedLongSynchronized中的state都是被定义为volatile来保证内存的可见性
     * @return
     */
    public static VolatileSingleton getInstance() {
        if (singleton == null) {
            synchronized (VolatileSingleton.class) {
                if (singleton == null) {
                    //防止指令重排
                    singleton = new VolatileSingleton();
                }
            }
        }
        return singleton;
    }
}
