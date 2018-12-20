package com.roedeer.spring.circleDependence.constructTest;

import com.roedeer.spring.circleDependence.StudentA;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Roedeer on 12/15/2018.
 */
public class circleDependenceTest {

    /**
     * 构造器循环依赖
     */
    @Test
    public void testConstruct() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/circleDependence/constructBean.xml");
        System.out.println(context.getBean("a", StudentA.class));
    }

    /**
     * 通过property的setter方法注入,单例模式,无依赖问题
     * DefaultSingletonBeanRegistry.java类
     */
    @Test
    public void testSetter() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/circleDependence/setterBean.xml");
        System.out.println(context.getBean("a", StudentA.class));
    }

    /**
     * 通过property的setter方法注入,prototype原型模式
     */
    @Test
    public void testPrototype() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/circleDependence/prototypeBean.xml");
        System.out.println(context.getBean("a", StudentA.class));
    }

}
