package com.roedeer.concurrent.volatileTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 断点进去查看源码,通过JNI(Java Native Interface)调用系统底层
 * CAS(Compare And Swap)实现, CAS(V,E,N) V:更新的变量 E:预期值  N:新值
 * CAS操作即  V == E ? V = N : NULL  如果V不等于E,代表有其他线程做了更新操作,当前线程什么都不做,重新获取V
 * 主要通过Unsafe类完成操作  https://juejin.im/entry/595c599e6fb9a06bc6042514
 *
 * @Author Roedeer
 * @Date 12/21/2018 9:22 AM
 **/
public class AtomicSource {
    public static void main(String[] args) {
        AtomicInteger inc = new AtomicInteger();
        System.out.println(inc);
        inc.getAndIncrement();
        System.out.println(inc);
        inc.incrementAndGet();
        System.out.println(inc);
    }
}
