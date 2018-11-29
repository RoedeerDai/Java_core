package com.roedeer.spring4.ch1.di;

import org.springframework.stereotype.Service;

/**
 * Created by Roedeer on 2018/6/12.
 */
@Service
public class FunctionService {
    public String sayHello(String word) {
        return "Hello" + word + "!";
    }
}
