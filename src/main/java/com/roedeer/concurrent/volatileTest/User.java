package com.roedeer.concurrent.volatileTest;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 12/21/2018 2:16 PM
 **/
public class User {

    private String name;
    private int age;
    private static String id = "USER_ID";

    public User() {
        System.out.println("构造方法被调用");
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age + '\'' +
                ", id=" + id + "\'" +
                '}';
    }
}
