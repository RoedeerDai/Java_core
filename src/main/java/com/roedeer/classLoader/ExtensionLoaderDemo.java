package com.roedeer.classLoader;

import java.io.File;
import java.util.StringTokenizer;

/**
 * @Description 扩展(Extension)类加载器
 * 负责加载<JAVA_HOME>/lib/ext目录下或者由系统变量-Djava.ext.dir指定位路径中的类库
 * @Author Roedeer
 * @Date 12/21/2018 3:15 PM
 **/
public class ExtensionLoaderDemo {

    public static void main(String[] args) {
        File[] dirs = getExtDirs();
        for (File file : dirs) {
            System.out.println(file.getName());
        }
    }

    //ExtClassLoader类中获取路径的代码
    private static File[] getExtDirs() {
        //加载<JAVA_HOME>/lib/ext目录中的类库
        String s = System.getProperty("java.ext.dirs");
        File[] dirs;
        if (s != null) {
            StringTokenizer st = new StringTokenizer(s, File.pathSeparator);
            int count = st.countTokens();
            dirs = new File[count];
            for (int i = 0; i < count; i++) {
                dirs[i] = new File(st.nextToken());
            }
        } else {
            dirs = new File[0];
        }
        return dirs;
    }
}
