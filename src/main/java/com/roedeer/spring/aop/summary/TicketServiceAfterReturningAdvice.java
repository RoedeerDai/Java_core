package com.roedeer.spring.aop.summary;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * 返回结果后的处理意见
 * Created by Roedeer on 11/24/2018.
 */
public class TicketServiceAfterReturningAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("AFTER_RETURNING: This service id end");
    }
}
