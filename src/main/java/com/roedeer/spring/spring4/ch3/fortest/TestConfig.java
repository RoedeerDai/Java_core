package com.roedeer.spring4.ch3.fortest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * Created by Roedeer on 2018/6/13.
 */
public class TestConfig {
    @Bean
    @Profile("dev")
    public TestBean devTestBean() {
        return new TestBean("from development profile");
    }

    @Bean
    @Profile("prod")
    public TestBean prodTestBean() {
        return new TestBean("from production profile");
    }
}
