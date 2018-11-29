package com.roedeer.spring.aop.dynamicProxy.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Roedeer on 11/24/2018.
 */
public class WaiterInvocationHandler implements InvocationHandler {

    public Waiter target;

    public WaiterInvocationHandler(Waiter target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Hello ");
        Object result = method.invoke(target, args);
        System.out.println("My pleasure");
        return result;
    }
}
