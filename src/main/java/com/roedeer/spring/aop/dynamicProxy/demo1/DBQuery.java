package com.roedeer.spring.aop.dynamicProxy.demo1;

/**
 * Created by Roedeer on 11/23/2018.
 */
public class DBQuery implements IDBQuery {

    public DBQuery() {
        try {
            Thread.sleep(1000);     //可能包含数据库连接等耗时操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String request() {
        return "request string";
    }
}
