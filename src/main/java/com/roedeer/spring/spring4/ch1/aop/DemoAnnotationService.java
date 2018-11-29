package com.roedeer.spring.spring4.ch1.aop;

import org.springframework.stereotype.Service;

/**
 * Created by Roedeer on 2018/6/12.
 * 使用注解的被拦截类
 */
@Service
public class DemoAnnotationService {
    @Action(name="注解式拦截的add操作")
    public void add(){}
}
