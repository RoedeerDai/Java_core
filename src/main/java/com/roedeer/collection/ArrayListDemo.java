package com.roedeer.collection;

import com.sun.jmx.remote.internal.ArrayQueue;
import org.junit.Test;

import java.util.*;

/**
 * Created by U6071369 on 10/18/2018.
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                break;
            }
            System.out.println(i);
        }
        byte a; Byte aa;
        short b; Short bb;
        int c;  Integer cc;
        long d; Long dd;
        float e; Float ee;
        double f; Double ff;
        char g; Character gg;
        boolean flag; Boolean hh;
        /**
         * 静态工厂方法valueOf
         * 自动装箱拆箱
         */

    }

    @Test
    public void test1() {
        ArrayList list = new ArrayList();
        LinkedList linkedList = new LinkedList();
        HashMap<String, String> map = new HashMap<String, String>();
        Collections.synchronizedMap(map);
        TreeMap<String, String> treeMap = new TreeMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>();
        ArrayQueue<String> arrayQueue = new ArrayQueue<>(10);

    }
}
