package com.roedeer.spring.spring4.ch2.event;

import com.roedeer.spring.spring4.ch2.event.EventConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Roedeer on 2018/6/12.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(EventConfig.class);
        DemoPublisher demoPublisher = annotationConfigApplicationContext.getBean(DemoPublisher.class);
        demoPublisher.publish("hello application event");
        annotationConfigApplicationContext.close();
    }
}
