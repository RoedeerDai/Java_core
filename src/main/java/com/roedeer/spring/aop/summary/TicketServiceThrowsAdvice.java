package com.roedeer.spring.aop.summary;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * 抛出异常时的处理意见
 * Created by Roedeer on 11/24/2018.
 */
public class TicketServiceThrowsAdvice implements ThrowsAdvice {

    public void afterThrowing(Exception ex) {
        System.out.println("AFTER_THROWING....");
    }

    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        System.out.println("Calls a mistake");
    }

}
