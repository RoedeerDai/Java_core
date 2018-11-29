package com.roedeer.spring.aop.summary;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * AroundAdvice
 * Created by Roedeer on 11/24/2018.
 */
public class TicketServiceAroundAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("AROUND_ADVICE:BEGIN....");
        Object returnValue = methodInvocation.proceed();
        System.out.println("AROUND_ADVICE:END.....");
        return returnValue;
    }
}
