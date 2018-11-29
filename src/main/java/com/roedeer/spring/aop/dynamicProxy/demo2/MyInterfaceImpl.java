package com.roedeer.spring.aop.dynamicProxy.demo2;

/**
 * Created by Roedeer on 11/24/2018.
 */
public class MyInterfaceImpl implements MyInterface {
    @Override
    public void fun1() {
        System.out.println("fun1()");
    }

    @Override
    public void fun2() {
        System.out.println("fun2()");
    }
}
