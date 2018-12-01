package com.roedeer.collection;

import org.junit.Test;

import java.util.HashMap;

public class CollectionTest {

    @Test
    public void testHashMap() {
        HashMap<String,Integer> map = new HashMap<>();
        map.put("1",1);
        System.out.println(map);
    }

}
