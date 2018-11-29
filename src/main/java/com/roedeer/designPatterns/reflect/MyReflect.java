package com.roedeer.designPatterns.reflect;


import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by U6071369 on 7/24/2018.
 */
public class MyReflect {

    public String className = null;

    public Class personClass = null;

    @Before
    public void init() throws ClassNotFoundException {
        className = "com.roedeer.reflect.Person";
        personClass = Class.forName(className);
    }

    @Test
    public void getClassName() {
        System.out.println(personClass);
    }

    @Test
    public void getClassName2() {
        System.out.println(Person.class);
    }

    @Test
    public void getNewInstance() throws IllegalAccessException, InstantiationException {
        System.out.println(personClass.newInstance());
    }

    @Test
    public void getPublicConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = personClass.getConstructor(Long.class,String.class);
        Person person = (Person) constructor.newInstance(100L,"zhangsan");
        System.out.println(person.getId() + "  " + person.getName());
    }

    @Test
    public void getPrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = personClass.getDeclaredConstructor(String.class);
        constructor.setAccessible(true); //强制取消java的权限检测
        Person person = (Person) constructor.newInstance("zhangsan");
        System.out.println(person.getId() + "  " + person.getName());
    }

    @Test
    public void getNotPrivateField() throws Exception {
        Constructor constructor = personClass.getConstructor(Long.class, String.class);
        Object object = constructor.newInstance(100L, "zhangsan");

        Field field = personClass.getField("name");
        field.set(object, "lisi");
        System.out.println(field.get(object));
    }

    @Test
    public void getPrivateField() throws Exception {
        Constructor constructor = personClass.getConstructor(Long.class);
        Object object = constructor.newInstance(100L);

        Field field = personClass.getDeclaredField("id");
        field.setAccessible(true);
        field.set(object,10000L);
        System.out.println(field.get(object));
    }

    @Test
    public void getNotPrivateMethod() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        System.out.println(personClass.getMethod("toString"));

        Object object = personClass.newInstance();
        Method toStringMethod = personClass.getMethod("toString");
        Object objects = toStringMethod.invoke(object);
        System.out.println(objects);
    }

    @Test
    public void getPrivateMethod() throws Exception {
        Object object = personClass.newInstance();
        Method method = personClass.getDeclaredMethod("getSomeThing");
        method.setAccessible(true);
        Object value = method.invoke(object);
        System.out.println(value);
    }

    @Test
    public void otherMethod() throws Exception {
        System.out.println(personClass.getClassLoader());

        Class[] interfaces = personClass.getInterfaces();
        for (Class class1 : interfaces) {
            System.out.println(class1);
        }

        System.out.println(personClass.getGenericSuperclass());

        /**
         * getResourceAsStream这个方法可以获取到一个输入流，这个输入流会关联到name所表示的那个文件上。
         */
        //path 不以’/'开头时默认是从此类所在的包下取资源，以’/'开头则是从ClassPath根下获取。其只是通过path构造一个绝对路径，最终还是由ClassLoader获取资源。
        System.out.println(personClass.getResourceAsStream("/log4j.properties"));
        System.out.println(personClass.getResourceAsStream("log4j.properties"));

        System.out.println(personClass.getResource("/log4j.properties").getPath());
        System.out.println(personClass.getResource("/cfg/test.txt").getPath());

        System.out.println(personClass.isArray());
        System.out.println(new String[3].getClass().isArray());

        System.out.println(personClass.isEnum());
        System.out.println(Class.forName("com.roedeer.reflect.City").isEnum());

        System.out.println(personClass.isInterface());
        System.out.println(Class.forName("com.roedeer.reflect.TestInterface"));
    }

}
