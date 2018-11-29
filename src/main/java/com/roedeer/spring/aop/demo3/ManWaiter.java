package com.roedeer.spring.aop.demo3;

/**
 * Created by Roedeer on 11/24/2018.
 */
public class ManWaiter implements Waiter {
    @Override
    public void serve() {
        System.out.println("Glad to be of service");
    }

    @Override
    public void checkOut() {
        System.out.println("A total of five dollars, thanks");
    }
}
