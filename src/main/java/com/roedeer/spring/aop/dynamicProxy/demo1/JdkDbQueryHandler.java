package com.roedeer.spring.aop.dynamicProxy.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理类的实现
 * Created by Roedeer on 11/23/2018.
 */
public class JdkDbQueryHandler implements InvocationHandler {

    IDBQuery real = null;   //主题接口

    /**
     * 生成handler
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (real == null) {
            real = new DBQuery();   //如果是第一次调用,则生成真实对象
        }
        return real.request();
    }

    /**
     * 利用handler生成动态代理对象
     */
    public static IDBQuery createJdkProxy() {
        //根据指定的类加载器和接口以及截获器，返回代理类的一个实例对象
        //ClassLoader loader :指定被代理对象的类加载器
        //Class[] Interfaces ：指定被代理对象所以事项的接口
        //InvocationHandler h :指定需要调用的InvocationHandler对象
        IDBQuery jdkProxy = (IDBQuery) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{IDBQuery.class},new JdkDbQueryHandler());
        return jdkProxy;
    }
}
