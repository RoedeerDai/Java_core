package com.roedeer.spring.aop.dynamicProxy.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Roedeer on 11/24/2018.
 */
public class JdkMyInterfaceHandler implements InvocationHandler {

    MyInterface myInterface;

    public JdkMyInterfaceHandler(MyInterface myInterface) {
        this.myInterface = myInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(myInterface,args);
        return result;
    }
}
