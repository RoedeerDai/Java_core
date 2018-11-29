package com.roedeer.spring.aop.cglib;

import com.roedeer.aop.dynamicProxy.demo1.DBQuery;
import com.roedeer.aop.dynamicProxy.demo1.IDBQuery;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB动态代理
 * Created by Roedeer on 11/24/2018.
 */
public class CglibDbQueryInterceptor implements MethodInterceptor {

    IDBQuery real = null;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (real == null) {     //代理类的内部逻辑
            real = new DBQuery();
            return real.request();
        }
        return null;
    }

    public static IDBQuery createCglibProxy() {
        Enhancer enhancer = new Enhancer();
        //指定切入器,定义代理逻辑
        enhancer.setCallback(new CglibDbQueryInterceptor());
        //指定实现的接口
        enhancer.setInterfaces(new Class[]{IDBQuery.class});

        IDBQuery cglibProxy = (IDBQuery) enhancer.create();
        return cglibProxy;
    }
}
