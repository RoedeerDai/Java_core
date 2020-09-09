package com.roedeer.algorithm;



/**
 * @Description 两个链表的第一个公共结点
 * @Author Roedeer
 * @Date 10/22/2019 11:21 AM
 **/
public class FindFirstCommonNode {

    public static void main(String[] args) {
        ListNode head1 = new ListNode();
        ListNode second1 = new ListNode();
        ListNode third1 = new ListNode();
        ListNode forth1 = new ListNode();
        ListNode fifth1 = new ListNode();
        ListNode head2 = new ListNode();
        ListNode second2 = new ListNode();
        ListNode third2 = new ListNode();
        ListNode forth2 = new ListNode();
        head1.nextNode = second1;
        second1.nextNode = third1;
        third1.nextNode = forth1;
        forth1.nextNode = fifth1;
        head2.nextNode = second2;
        second2.nextNode = forth1;
        third2.nextNode = fifth1;
        head1.data = 1;
        second1.data = 2;
        third1.data = 3;
        forth1.data = 6;
        fifth1.data = 7;
        head2.data = 4;
        second2.data = 5;
        third2.data = 6;
        forth2.data = 7;
        FindFirstCommonNode node = new FindFirstCommonNode();
        System.out.println(node.findNode(head1,head2).data);
    }

    public ListNode findNode(ListNode head1, ListNode head2) {
        int len1 = getListLength(head1);
        int len2 = getListLength(head2);
        ListNode longListNode = null;
        ListNode shortListNode = null;
        int dif = 0;
        if (len1 > len2) {
            longListNode = head1;
            shortListNode = head2;
            dif = len1 - len2;
        } else {
            longListNode = head2;
            shortListNode = head1;
            dif = len2 - len1;
        }
        for (int i = 0; i < dif; i++) {
            longListNode = longListNode.nextNode;
        }
        while (longListNode != null && shortListNode != null && longListNode != shortListNode) {
            longListNode = longListNode.nextNode;
            shortListNode = shortListNode.nextNode;
        }
        return longListNode;
    }

    public int getListLength(ListNode head1) {
        int result = 0;
        if (head1 == null)
            return result;
        ListNode point = head1;
        while (point != null) {
            point = point.nextNode;
            result++;
        }
        return result;
    }
}

class ListNode {
    int data;
    ListNode nextNode;
}
