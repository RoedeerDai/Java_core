package com.roedeer.spring.spring4.ch1.javaconfig;


import org.springframework.stereotype.Service;

/**
 * Created by Roedeer on 2018/6/12.
 */
@Service
public class UseFunctionService {

    FunctionService functionService;

    public void setFunctionService(FunctionService functionService) {
        this.functionService = functionService;
    }

    public String SayHello(String word) {
        return functionService.sayHello(word);
    }
}
