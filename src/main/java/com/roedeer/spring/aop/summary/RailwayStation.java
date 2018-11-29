package com.roedeer.spring.aop.summary;

/**
 * RailwayStation实现TicketService
 * Created by Roedeer on 11/24/2018.
 */
public class RailwayStation implements TicketService {

    @Override
    public void sellTicket() {
        System.out.println("sell Ticket......");
    }

    @Override
    public void inquire() {
        System.out.println("inquire somebody .....");
    }

    @Override
    public void withdraw() {
        System.out.println("withdraw......");
    }
}
