package com.roedeer.spring.aop.dynamicProxy.demo2;

/**
 * Created by Roedeer on 11/24/2018.
 */
public class MyWaiter implements Waiter {
    @Override
    public void serve() {
        System.out.println("service...");
    }
}
