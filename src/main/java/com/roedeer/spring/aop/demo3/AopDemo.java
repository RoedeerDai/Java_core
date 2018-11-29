package com.roedeer.spring.aop.demo3;

import org.junit.Test;

/**
 * Created by Roedeer on 11/24/2018.
 */
public class AopDemo {
    @Test
    public void fun1() {
        ProxyFactory factory = new ProxyFactory();  //创建工厂
        factory.setTargetObject(new ManWaiter());   //设置目标对象
        factory.setBeforeAdvice(new BeforeAdvice() {
            @Override
            public void before() {
                System.out.println("Can I help you?");
            }
        });
        factory.setAfterAdvice(new AfterAdvice() {
            @Override
            public void after() {
                System.out.println("Look forward to your next Visit!");
            }
        });
        Waiter waiter = (Waiter) factory.createProxy();
        waiter.checkOut();
    }
}
