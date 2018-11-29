package com.roedeer.spring.aop.summary;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 执行RealSubject对象的方法之前的处理意见
 * Created by Roedeer on 11/24/2018.
 */
public class TicketServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("BEFORE_ADVICE: Welcome to the station");
    }
}
