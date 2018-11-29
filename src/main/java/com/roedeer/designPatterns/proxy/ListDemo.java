package com.roedeer.designPatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by U6071369 on 7/18/2018.
 */
public class ListDemo {
    public static void main(String[] args) throws Exception {
        final List list = new ArrayList();
        Object oo = Proxy.newProxyInstance(List.class.getClassLoader(),
                list.getClass().getInterfaces(), new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.err.println("add an object");
                        Object returnValue = method.invoke(list, args);
                        System.err.println("add end");
                        if (method.getName().equals("size")) {
                            return 100;
                        }
                        return returnValue;
                    }
                });
        List list2 = (List) oo;
        list2.add("aaa");
        list2.add("bbb");

        System.err.println("size:" + list2.size() + "," + list.size());
        System.out.println(list2.get(0) + "  " + list2.get(3));
    }
}
