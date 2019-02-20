package com.roedeer.classLoader;

import sun.applet.AppletClassLoader;

/**             https://blog.csdn.net/javazejian/article/details/73413292
 * @Description 在JVM中表示两个class对象是否为同一个类对象存在两个必要条件
 *              1.类的完整类名必须一致,包括包名
 *              2.加载这个类的ClassLoader(指ClassLoader实例对象)必须相同
 *
 *              也就是说,在JVM中,即使这个两个类对象(class对象)来源同一个Class文件,被同一个虚拟机所加载,
 *              但只要加载它们的ClassLoader实例对象不同,那么这两个类对象也是不相等的,这是因为不同的ClassLoader实例对象都拥有不同的独立的类名称空间,
 *              所以加载的class对象也会存在不同的类名空间中,但前提是覆写loadclass方法,从前面双亲委派模式对loadClass()方法的源码分析中可以知,
 *              在方法第一步会通过Class<?> c = findLoadedClass(name);从缓存查找,类名完整名称相同则不会再次被加载,因此我们必须绕过缓存查询才能重新加载class对象
 *              当然也可直接调用findClass()方法，这样也避免从缓存查找，如下
 * @Author Roedeer
 * @Date 12/21/2018 4:50 PM
 **/
public class SameClassTest {
    public static void main(String[] args) {
        String rootDir = "D:\\IDEA_workplace\\Java_Core\\target\\classes";
        //创建两个不同的自定义类加载器实例
        FileClassLoader loader1 = new FileClassLoader(rootDir);
        FileClassLoader loader2 = new FileClassLoader(rootDir);

        // 通过findClass创建类的Class对象
        try {
            Class<?> object1 = loader1.findClass("com.roedeer.classLoader.DemoObj");
            Class<?> object2 = loader2.findClass("com.roedeer.classLoader.DemoObj");
            System.out.println("findClass--->obj1:" + object1.hashCode());
            System.out.println("findClass--->obj2:" + object2.hashCode());

            //直接调用父类的loadClass()方法
            Class<?> object3 = loader1.loadClass("com.roedeer.classLoader.DemoObj");
            Class<?> object4 = loader2.loadClass("com.roedeer.classLoader.DemoObj");
            System.out.println("Parent loadClass--->obj3:" + object3.hashCode());
            System.out.println("Parent loadClass--->obj4:" + object4.hashCode());
            //系统类加载器
            System.out.println("System ClassLoader class-->obj5:" + DemoObj.class.hashCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
