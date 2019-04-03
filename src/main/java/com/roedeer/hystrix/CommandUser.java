package com.roedeer.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


/**
 * @Description 用户服务
 * @Author Roedeer
 * @Date 2/26/2019 10:27 AM
 **/
public class CommandUser extends HystrixCommand<String> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommandUser.class);

    private String userName;

    public CommandUser(String userName) {
        super(Setter.withGroupKey(
                //服务分组
                HystrixCommandGroupKey.Factory.asKey("UserGroup"))
                //线程分组
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("UserPool"))

                //线程池配置
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10))
        );
        this.userName = userName;
    }

    @Override
    protected String run() throws Exception {
        LOGGER.info("userName=[{}]", userName);

        TimeUnit.MILLISECONDS.sleep(100);
        return "userName=" + userName;
    }
}
