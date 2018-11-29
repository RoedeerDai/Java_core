package com.roedeer.spring.aop.dynamicProxy.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Roedeer on 11/24/2018.
 * 针对接口编程,此时只需要handler类实现InvocationHandler接口,在handler中生成接口的impl对象就可以调用impl对象的方法
 * 可以通过反射在handler中生成impl对象
 */
public class Application {
    public static void main(String[] args) {
        Class[] classInterface = {MyInterface.class};
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("当前调用的方法是:" + method.getName());
                System.out.println("无论调用什么方法,都是在调用invoke方法...");
                return null;
            }
        };
//        MyInterface myInterface = (MyInterface) Proxy.newProxyInstance(loader,classInterface,handler);
        MyInterface myInterface = (MyInterface) Proxy.newProxyInstance(loader,classInterface,new JdkMyInterfaceHandler(new MyInterfaceImpl()));
        myInterface.fun1();
//        myInterface.fun2();
    }
}
