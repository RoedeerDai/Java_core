package com.roedeer.designPatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by U6071369 on 7/18/2018.
 */
public class ProxyPerson {
    public static void main(String[] args) {
        final MyPerson p = new MyPerson();
        PersonInterface proxy = (PersonInterface) Proxy.newProxyInstance(MyPerson.class.getClassLoader(), MyPerson.class.getInterfaces(), new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("proxy is coming....");
                if (method.getName() == "saySomeThing") {
                    System.out.println("say some thing is special handled.....");
                    p.saySomeThing();
                } else {
                    Object invoke = method.invoke(p, args);
                    System.out.println("proxy is leaving.....");
                }
                return null;
            }
        });
        proxy.doSomeThing();
        proxy.saySomeThing();
    }
}
