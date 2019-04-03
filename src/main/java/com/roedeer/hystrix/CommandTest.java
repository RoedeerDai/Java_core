package com.roedeer.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Description 线程隔离测试
 * @Author Roedeer
 * @Date 2/26/2019 10:46 AM
 **/
public class CommandTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandTest.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        CommandOrder commandPhone = new CommandOrder("手机");
        CommandOrder command = new CommandOrder("电视");

        //阻塞方式执行
        String execute = commandPhone.execute();
        LOGGER.info("execute=[{}]", execute);

        //异步非阻塞方式
        Future<String> queue = command.queue();
        String value = queue.get(200, TimeUnit.MILLISECONDS);
        LOGGER.info("value=[{}]", value);

        CommandUser commandUser = new CommandUser("张三");
        String name = commandUser.execute();
        LOGGER.info("name=[{}]", name);
    }
}
