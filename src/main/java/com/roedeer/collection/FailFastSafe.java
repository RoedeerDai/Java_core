package com.roedeer.collection;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 1/23/2019 5:49 PM
 **/
public class FailFastSafe {


    /**

     一：快速失败（fail—fast）

     在用迭代器遍历一个集合对象时，如果遍历过程中对集合对象的内容进行了修改（增加、删除、修改），则会抛出Concurrent Modification Exception。

     原理：迭代器在遍历时直接访问集合中的内容，并且在遍历过程中使用一个 modCount 变量。集合在被遍历期间如果内容发生变化，就会改变modCount的值。每当迭代器使用hashNext()/next()遍历下一个元素之前，都会检测modCount变量是否为expectedmodCount值，是的话就返回遍历；否则抛出异常，终止遍历。

     注意：这里异常的抛出条件是检测到 modCount！=expectedmodCount 这个条件。如果集合发生变化时修改modCount值刚好又设置为了expectedmodCount值，则异常不会抛出。因此，不能依赖于这个异常是否抛出而进行并发操作的编程，这个异常只建议用于检测并发修改的bug。

     场景：java.util包下的集合类都是快速失败的，不能在多线程下发生并发修改（迭代过程中被修改）。

     二：安全失败（fail—safe）

     采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。

     原理：由于迭代时是对原集合的拷贝进行遍历，所以在遍历过程中对原集合所作的修改并不能被迭代器检测到，所以不会触发Concurrent Modification Exception。

     缺点：基于拷贝内容的优点是避免了Concurrent Modification Exception，但同样地，迭代器并不能访问到修改后的内容，即：迭代器遍历的是开始遍历那一刻拿到的集合拷贝，在遍历期间原集合发生的修改迭代器是不知道的。

     场景：java.util.concurrent包下的容器都是安全失败，可以在多线程下并发使用，并发修改。
     */


    @Test
    public void testFailFast() {
        Map<String,String> premiumPhone = new HashMap<String,String>();
        premiumPhone.put("Apple", "iPhone");
        premiumPhone.put("HTC", "HTC one");
        premiumPhone.put("Samsung","S5");

        Iterator iterator = premiumPhone.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println(entry.getKey() + "--" + entry.getValue());
            premiumPhone.put("Sony", "Xperia Z");
        }

    }

    @Test
    public void testFailSafe() {
        ConcurrentHashMap<String,String> premiumPhone = new ConcurrentHashMap<String,String>();
        premiumPhone.put("Apple", "iPhone");
        premiumPhone.put("HTC", "HTC one");
        premiumPhone.put("Samsung","S5");

        Iterator iterator = premiumPhone.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println(entry.getKey() + "---" + entry.getValue());
            premiumPhone.put("Sony", "Xperia Z");
        }

    }

    /**
     * subList是ArrayList的内部类
     */
    @Test
    public void SubListFailFast() {
        List masterList = new ArrayList();
        masterList.add("one");
        masterList.add("two");
        masterList.add("three");
        masterList.add("four");
        masterList.add("five");

        List branchList = masterList.subList(0, 3);
        System.out.println("branchList = " + branchList);
        System.out.println("masterList = " + masterList);

        //下面三行会引起异常
//        masterList.remove(0);
//        masterList.add("ten");
//        masterList.clear();

        branchList.clear();
        System.out.println("branchList = " + branchList);
        System.out.println("masterList = " + masterList);
        branchList.add("six");
        branchList.add("seven");
        System.out.println("branchList = " + branchList);
        System.out.println("masterList = " + masterList);
        branchList.remove(0);
        System.out.println("branchList = " + branchList);
        System.out.println("masterList = " + masterList);

        for (Object t : branchList) {
            System.out.println(t);
        }

        System.out.println(masterList);
    }

    /**
     * CopyOnWriteArrayList COW家族 并发容器 读写分离,写操作会复制一个新集合
     * COW适合读多写极少的情况,频繁的写性能极地,要么批量的写入,使用ArrayList作为参数填充
     * 并发容器都是fail-safe机制,缺点无法读取到最新的数据
     * 这也是CAP理论中C(Consistency)与A(Availability)的矛盾,一致性和可用性的矛盾
     */
    @Test
    public void testCopyOnWrite() {
        List<Long> copy = new CopyOnWriteArrayList<>();

        long start = System.nanoTime();
        for (int i = 0; i < 10 * 10000; i++) {
            copy.add(System.nanoTime());
        }
        long end = System.nanoTime();
        System.out.println("Total time = " + (end - start) / (1000.0 * 1000.0) + "ms");
    }


}
