package com.roedeer.classLoader;

import org.junit.Test;

import java.lang.reflect.Field;
import java.net.URLClassLoader;

/**              https://blog.csdn.net/javazejian/article/details/73413292
 * @Description  启动类加载器,由C++实现,没有父类
 *               拓展类加载器(ExtClassLoader),由Java语言实现,父类加载器为null
 *               系统类加载器(AppClassLoader),由Java语言实现,父类加载器为ExtClassLoader
 *               自定义类加载器,父类加载器肯定为AppClassLoader
 * @Author Roedeer
 * @Date 12/21/2018 3:45 PM
 **/
public class ClassLoaderTest {

    public static void main(String[] args) {

//        String rootDir = "D:/IDEA_workplace/Java_Core/src/main/java";
        String rootDir = "D:\\IDEA_workplace\\Java_Core\\target\\classes";

        FileClassLoader loader = new FileClassLoader(rootDir);

        try {
            Class<?> object = loader.findClass("com.roedeer.classLoader.DemoObj");
            System.out.println(object.hashCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("自定义类加载器的父加载器: "+loader.getParent());
        System.out.println("系统默认的AppClassLoader: "+ClassLoader.getSystemClassLoader());
        System.out.println("AppClassLoader的父类加载器: "+ClassLoader.getSystemClassLoader().getParent());
        System.out.println("ExtClassLoader的父类加载器: "+ClassLoader.getSystemClassLoader().getParent().getParent());

        /**
         输出结果:
         自定义类加载器的父加载器: sun.misc.Launcher$AppClassLoader@18b4aac2
         系统默认的AppClassLoader: sun.misc.Launcher$AppClassLoader@18b4aac2
         AppClassLoader的父类加载器: sun.misc.Launcher$ExtClassLoader@76ed5528
         ExtClassLoader的父类加载器: null
         */
    }
}
