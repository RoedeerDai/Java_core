package com.roedeer.spring.spring4.ch1.aop;

import java.lang.annotation.*;

/**
 * Created by Roedeer on 2018/6/12.
 * 拦截规则的注解
 * 注解本身没有功能，和xml一样，注解和xml都是元数据，元数据即解释数据的数据
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
    String name();
}
