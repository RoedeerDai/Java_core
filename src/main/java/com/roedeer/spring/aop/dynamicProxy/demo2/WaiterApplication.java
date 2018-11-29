package com.roedeer.spring.aop.dynamicProxy.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by Roedeer on 11/24/2018.
 */
public class WaiterApplication {
    public static void main(String[] args) {
        Waiter waiter = (Waiter) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{Waiter.class}, new WaiterInvocationHandler(new MyWaiter()));
        waiter.serve();
    }
}

