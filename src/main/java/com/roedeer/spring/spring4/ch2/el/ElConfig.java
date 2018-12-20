package com.roedeer.spring.spring4.ch2.el;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

/**
 * Created by Roedeer on 2018/6/12.
 */
@Configuration
@ComponentScan("com.roedeer.spring.spring4.ch2.el")
@PropertySource("classpath:config/test.txt")
public class ElConfig {

    @Value("I love you")
    private String normal;

    @Value("#{systemProperties['os.name']}")  //注入操作系统属性
    private String osName;

    @Value("#{ T(java.lang.Math).random() * 100.0 }") //注入表达式
    private double randomNumber;

    @Value("#{demoService.another}") //注入其他Bean属性
    private String fromAnother;

    @Value("classpath:config/test.txt") //注入文件资源
    private Resource testFile;

    @Value("http://www.baidu.com") //注入网址
    private Resource testUrl;

    @Value("${book.name}") //注入配置文件
    private String bookName;

    @Autowired
    private Environment environment; //7

    @Autowired
    private DemoService demoService;

    @Bean //7
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public void outputResource() {
        try {
            System.out.println(normal);
            System.out.println(osName);
            System.out.println(randomNumber);
            System.out.println(fromAnother);

            System.out.println(IOUtils.toString(testFile.getInputStream(),"UTF-8"));
            System.out.println(IOUtils.toString(testUrl.getInputStream(),"UTF-8"));
            System.out.println(bookName);
            System.out.println(environment.getProperty("book.author"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void callDemoService() {
        demoService.print();
    }
}
