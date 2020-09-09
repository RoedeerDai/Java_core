package com.roedeer.algorithm;


public class PrintListReverse {

    public static void main(String[] args) {
        ListNode node1 = new ListNode();
        ListNode node2 = new ListNode();
        ListNode node3 = new ListNode();
        node1.data = 1;
        node2.data = 2;
        node3.data = 3;
        node1.nextNode = node2;
        node2.nextNode = node3;

    }

}
