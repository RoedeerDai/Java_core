package com.roedeer.collection;

import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description 集合的泛型
 * @Author Roedeer
 * @Date 3/4/2019 3:47 PM
 **/
public class ListNoGeneric {
    public static void main(String[] args) {
        //泛型出现之前集合定义
        List a1 = new ArrayList();
        a1.add(new Object());
        a1.add(new Integer(111));
        a1.add(new String("hello a1a1"));

        //增加泛型限制Object
        List<Object> a2 = a1;
        a2.add(new Object());
        a2.add(new Integer(222));
        a2.add(new String("hello a2a2"));

        //增加了泛型<Integer>
        List<Integer> a3 = a1;
        a3.add(new Integer(333));
        //下方编译出错,不允许增加非Integer类型进入集合
//        a3.add(new Object());
//        a3.add(new String("hello a3a3"));

        //增加了通配符 ?
        List<?> a4 = a1;
        //允许删除和清除元素
        a1.remove(0);
        a4.clear();
        //编译出错,不允许增加任何元素
//        a4.add(new Object());

    }


    /**
     * 实际问题代码,会报错,所以应该尽量使用泛型定义
     */
    @Test
    public void testDemo() {
        JSONObject jsonObject = JSONObject.fromObject("{\"level\":[\"3\"]}");
        List<Integer> intList = new ArrayList<>(10);

        if (jsonObject != null) {
            intList.addAll(jsonObject.getJSONArray("level"));
            int amount = 0;
//            for (Integer t : intList) {  //java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Integer
//                if (true) {
//                    amount += t;
//                }
//            }
        }
    }


    /**
     * <? extends T>和<? super T>两种语法的demo
     * <? extends T>适应于消费集合元素为主的场景,除null外,任何元素都不得添加进<? extends T>集合内
     * <? super T>适应于生产集合元素为主的场景,类似投票选举,取数据时不知道是谁的票,相当于泛型丢失
     */
    @Test
    public void testAnimalCatCarfield() {
        //第1段
        List<Animal> animals = new ArrayList<Animal>();
        List<Cat> cats = new ArrayList<Cat>();
        List<Garfield> garfields = new ArrayList<Garfield>();

        animals.add(new Animal());
        cats.add(new Cat());
        garfields.add(new Garfield());

        //2.测试赋值操作,下行编译出错,只能赋值Cat或Cat子类的集合
//        List<? extends Cat> extendsCatFromAnimal = animals;
        List<? super Cat> superCatFromAnimal = animals;

        List<? extends Cat> extendsCatFromCat = cats;
        List<? super Cat> superCatFromCat = cats;

        List<? extends Cat> extendsCatFromGarfield = garfields;
        //下行编译出错,只能赋值Cat或Cat的父类的集合
//        List<? super Cat> superCatFromGarfield = garfields;

        //3.测试add方法,下面均出错
//        extendsCatFromCat.add(new Animal());
//        extendsCatFromCat.add(new Cat());
//        extendsCatFromCat.add(new Garfield());

        //下行编译出错,只能添加cat或者cat的子类的集合
//        superCatFromCat.add(new Animal());
        superCatFromCat.add(new Cat());
        superCatFromCat.add(new Garfield());

        superCatFromAnimal.add(new Cat());

        //第4段,测试get方法
        //所有的super操作能够返回元素,但是泛型丢失,只能返回object对象

        Object catExtends2 = extendsCatFromCat.get(0);
        Cat catExtends1 = extendsCatFromCat.get(0);
        //下行编译出错,因为Cat集合从Garfield赋值而来,但类型擦除后,是不知道的
//        Garfield garfield1 = extendsCatFromGarfield.get(0);

    }

    class Animal { }

    class Cat extends Animal { }

    class Garfield extends Cat { }

}
