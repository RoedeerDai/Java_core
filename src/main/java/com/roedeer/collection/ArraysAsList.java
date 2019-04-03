package com.roedeer.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 数组和list
 * @Author Roedeer
 * @Date 3/4/2019 3:04 PM
 **/
public class ArraysAsList {
    public static void main(String[] args) {
        String[] stringArray = new String[3];
        stringArray[0] = "one";
        stringArray[1] = "two";
        stringArray[2] = "three";

        //遍历推荐使用foreach,jdk8的函数式接口
        Arrays.asList(stringArray).stream().forEach(x -> System.out.printf(x));

        /**
         * Arrays是针对数组对象进行操作的工具类,包括数组的排序、查找、对比、拷贝等操作
         * 尤其是排序,把原来的归并排序改为Timsort
         * 数组转集合,返回的ArrayList是属于Arrays的内部类,这个内部类只实现了集合的set方法,没有实现集合个数的相关修改方法
         * 报出的UnsupportedOperationException异常是由父类AbstractList抛出的
         */
        List<String> stringList = Arrays.asList(stringArray);
//        List<Object> objectList = new ArrayList<>(Arrays.asList(stringArray));  //避免删除的UnsupportedOperationException异常
        stringList.set(0, "oneList");
        System.out.println(stringArray[0]);

        stringList.add("four");
        stringList.remove(2);
        stringList.clear();
    }

    /**
     * list转数组
     */
    @Test
    public void ListToArray() {
        List<String> list = new ArrayList<>(3);
        list.add("one");
        list.add("two");
        list.add("three");

        //不要使用toArray()无参方法把集合转换成数组,这样会导致泛型丢失,无法使用String[]接受返回参数
        Object[] array1 = list.toArray();

        //容量不够
        String[] array2 = new String[2];
        list.toArray(array2);
        System.out.println(Arrays.asList(array2));

        //容量相等
        String[] array3 = new String[3];
        list.toArray(array3);
        System.out.println(Arrays.asList(array3));
    }

    /**
     * list转数组效率,数组容量等于集合大小最快
     */
    @Test
    public void testPerfomance() {
        final int count = 100 * 100 * 100;
        List<Double> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(i * 1.0);
        }

        long start = System.nanoTime();

        Double[] notEnouthArray = new Double[count - 1];
        list.toArray(notEnouthArray);

        long middle1 = System.nanoTime();

        Double[] equalArray = new Double[count];
        list.toArray(equalArray);

        long middle2 = System.nanoTime();

        Double[] doubleArray = new Double[count * 2];
        list.toArray(doubleArray);

        long end = System.nanoTime();

        long notEnoughArrayTime = middle1 - start;
        long equalArrayTime = middle2 - middle1;
        long doubleArrayTime = end - middle2;

        System.out.println("数组容量小于集合大小: notEnoughArrayTime:" + notEnoughArrayTime / (1000.0 * 1000.0) + " ms");
        System.out.println("数组容量等于集合大小: equalArrayTime:" + equalArrayTime / (1000.0 * 1000.0) + " ms");
        System.out.println("数组容量是集合大小两倍: doubleArrayTime:" + doubleArrayTime / (1000.0 * 1000.0) + " ms");

    }


}
