package com.roedeer.spring4.ch1.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Roedeer on 2018/6/12.
 * 使用注解的被拦截类
 */

@Configuration
@ComponentScan("com.roedeer.spring4.ch1.aop")
@EnableAspectJAutoProxy //1
public class AopConfig {
}
