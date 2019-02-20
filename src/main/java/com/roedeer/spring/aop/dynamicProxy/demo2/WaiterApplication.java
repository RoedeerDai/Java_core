package com.roedeer.spring.aop.dynamicProxy.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by Roedeer on 11/24/2018.
 */
public class WaiterApplication {
    public static void main(String[] args) {
        MyWaiter myWaiter = new MyWaiter();
        WaiterInvocationHandler handler = new WaiterInvocationHandler(myWaiter);
        Waiter waiter = (Waiter) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),//
//                new Class[]{Waiter.class},
                MyWaiter.class.getInterfaces(),
                handler);
        waiter.serve();
    }
}

