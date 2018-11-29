package com.roedeer.spring.aop.summary;

import org.aopalliance.aop.Advice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;

/**
 * 通过ProxyFactoryBean 手动创建 代理对象
 * 理解问题：
 * 1.代理对象是怎么生成的?  JDK or Cglib
 * 2.Advice链(即拦截器链)的构造过程和执行机制
 * 3.如何在Advice上添加pointcut,并且这个pointcut是如何工作的?(实际上起到过滤作用)
 * Created by Roedeer on 11/24/2018.
 */
public class App {

    public static void main(String[] args) {

        //1. 针对不同的时期类型,提供不同的Advice
        Advice beforeAdvice = new TicketServiceBeforeAdvice();
        Advice afterReturningAdvice = new TicketServiceAfterReturningAdvice();
        Advice aroundAdvice = new TicketServiceAroundAdvice();
        Advice throwsAdvice = new TicketServiceThrowsAdvice();

        RailwayStation railwayStation = new RailwayStation();

        //2. 创建ProxyFactoryBean,用以创建指定对象的Proxy对象
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        //3. 设置Proxy的接口
        proxyFactoryBean.setInterfaces(TicketService.class);
        //4. 设置RealSubject
        proxyFactoryBean.setTarget(railwayStation);
        //5.使用JDK基于接口实现机制的动态代理生成Proxy代理对象，如果想使用CGLIB，需要将这个flag设置成true
        proxyFactoryBean.setProxyTargetClass(true);

        //6. 添加不同的Advice
//        proxyFactoryBean.addAdvice(afterReturningAdvice);
//        proxyFactoryBean.addAdvice(aroundAdvice);
//        proxyFactoryBean.addAdvice(throwsAdvice);
//        proxyFactoryBean.addAdvice(beforeAdvice);
//        proxyFactoryBean.setProxyTargetClass(false);

        //手动创建一个pointcut,专门拦截sellTicket方法
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution( * sellTicket(..))");
        //传入创建的beforeAdvice和pointcut
        FilteredAdvisor sellBeforeAdvisor = new FilteredAdvisor(pointcut,beforeAdvice);
        //添加到FactoryBean中
        proxyFactoryBean.addAdvisor(sellBeforeAdvisor);

        //7. 通过ProxyFactoryBean生成Proxy对象
        TicketService ticketService = (TicketService) proxyFactoryBean.getObject();
        ticketService.sellTicket();
        System.out.println("-------------");
        ticketService.inquire();
    }
}
