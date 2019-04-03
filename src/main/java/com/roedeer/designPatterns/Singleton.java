package com.roedeer.designPatterns;

/**
 * @Description singleton, double check
 * @Author Roedeer
 * @Date 3/26/2019 11:19 AM
 **/
public class Singleton {
    private static volatile Singleton singleton = null;
    private Singleton() {}

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
