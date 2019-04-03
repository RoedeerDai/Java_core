package com.roedeer.collection;

import org.junit.Test;

import java.util.TreeMap;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 3/5/2019 9:42 AM
 **/
public class MapStart {


    @Test
    public void treeMapRepeat() {
        //
        TreeMap map = new TreeMap();
        map.put(new Key(), "value one");
        map.put(new Key(), "value two");
        System.out.println(map.size());

    }

}

class Key implements Comparable<Key> {

    //返回负的常数,表示此对象永远小于输入的other对象,此处决定TreeMap的size=2
    @Override
    public int compareTo(Key o) {
        return -1;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
