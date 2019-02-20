package com.roedeer.classLoader;

import org.junit.Test;

import java.io.*;

/**              https://blog.csdn.net/javazejian/article/details/73413292
 * @Description 自己的类加载器,继承ClassLoader或者URLClassLoader
 *              继承ClassLoader需要重写findClass()方法并编写加载逻辑
 *              什么时候需要编写自定义类加载器:
 *              1.当class文件不在ClassPath路径下,默认系统类加载器无法找到该class文件,
 *              在这种情况下我们需要实现一个自定义的ClassLoader来加载特定路径下的class文件生成class对象
 *              2.当一个class文件是通过网络传输并且可能会进行相应的加密操作时,需要先对class文件进行相应的解密后再加载到JVM内存中
 *              这种情况下也需要编写自定义的ClassLoader并实现相应的逻辑
 *              3.当需要实现热部署功能时(一个class文件通过不同的类加载器产生不同class对象从而实现热部署功能),
 *              需要实现自定义ClassLoader的逻辑
 *
 *
 * @Author Roedeer
 * @Date 12/21/2018 4:21 PM
 **/
public class FileClassLoader extends ClassLoader {

    private String rootDir;

    public FileClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    /**
     * 编写findClass方法的逻辑
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //获取类的class文件字节数组
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            //直接生成class对象
            return defineClass(name, classData, 0, classData.length);
        }
    }

    /**
     * 编写获取class文件并转换为字节码流的逻辑
     * @param className
     * @return
     */
    private byte[] getClassData(String className) {
        //读取类文件的字节
        String path = classNameToPath(className);
        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int buffersize = 4096;
            byte[] buffer = new byte[buffersize];
            int bytesNumRead = 0;
            // 读取类文件的字节码
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 类文件的完全路径
     * @param className
     * @return
     */
    private String classNameToPath(String className) {
        return rootDir + File.separator
                + className.replace('.', File.separatorChar) + ".class";
    }

    public static void main(String[] args) {
//        String rootDir = "D:/IDEA_workplace/Java_Core/src/main/java";
        String rootDir = "D:\\IDEA_workplace\\Java_Core\\target\\classes";
        //创建自定义文件类加载器
        FileClassLoader loader = new FileClassLoader(rootDir);

        try {
            //加载指定的class文件
            Class<?> object = loader.loadClass("com.roedeer.classLoader.DemoObj");
            System.out.println(object.newInstance().toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
