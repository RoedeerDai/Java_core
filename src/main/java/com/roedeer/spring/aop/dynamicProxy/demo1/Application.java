package com.roedeer.spring.aop.dynamicProxy.demo1;

/**
 * Created by Roedeer on 11/24/2018.
 */
public class Application {
    public static void main(String[] args) {
        JdkDbQueryHandler handler = new JdkDbQueryHandler();
        IDBQuery query = handler.createJdkProxy();
        System.out.println(query.request());
    }
}
