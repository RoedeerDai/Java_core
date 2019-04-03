package com.roedeer.redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 3/11/2019 5:45 PM
 **/
public class TestRedis {

    public static void main(String[] args) {
//        Jedis jedis = new Jedis("47.107.247.140", 6379);
        Jedis jedis = new Jedis("192.168.91.128", 56379);
//        jedis.auth("123456");
        System.out.println(jedis);
        System.out.println("connect success");
        System.out.println("redis service running " + jedis.get("name"));

    }

}
