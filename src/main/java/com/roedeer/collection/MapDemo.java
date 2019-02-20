package com.roedeer.collection;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 1/23/2019 6:20 PM
 **/
public class MapDemo {


    /**
     * 利用LinkedHashMap构建一个空间占用敏感的资源池,希望可以将最不常被访问的对象释放掉
     */
    @Test
    public void LinkedHashMapSample() {
        LinkedHashMap<String, String> accessOrderedMap = new LinkedHashMap<String, String>(16,0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) { //自定义删除策略
                return size() > 3;
            }
        };
        accessOrderedMap.put("Project1","Valhalla");
        accessOrderedMap.put("Project2","Panama");
        accessOrderedMap.put("Project3","Loom");
        accessOrderedMap.forEach( (k,v) -> {
            System.out.println(k + ":" + v);
        });
        //模拟访问
        accessOrderedMap.get("Project1");
        accessOrderedMap.get("Project1");
        accessOrderedMap.get("Project3");
        System.out.println("Iterate over should be not affected:");
        accessOrderedMap.forEach( (k,v) -> {
            System.out.println(k + ":" + v);
        });
        //触发删除
        accessOrderedMap.put("Project4", "Mission Control");
        System.out.println("Oldest entry should be removed");
        accessOrderedMap.forEach( (k,v) -> { //遍历顺序不变
            System.out.println(k + ":" + v);
        });

    }

    /**
     * HashMap内部实现基本点
     * 容量(capacity)和负载系数(load factor)
     * 树化 -- 哈希值相同的键值对链表化,长度过长影响性能,将链表转化为红黑树
     */
    @Test
    public void testHashMap() {

    }

}
