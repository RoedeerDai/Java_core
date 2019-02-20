package com.roedeer.io;

import java.io.*;

/**
 * Created by U6071369 on 8/10/2018.
 */
public class TextFileTest {
    public static void main(String[] args) {
        String filePath = TextFileTest.class.getClassLoader().getResource("log4j.properties").getPath();
//        String path = TextFileTest.class.getClassLoader()
        System.out.println(filePath);
        String path = Thread.currentThread().getContextClassLoader().getResource("config/test.txt").getPath();
        String content = readFileByChars("src/main/resources/config/test.txt");
        System.out.println(readFileByChars(path));
        System.out.println(System.getProperty("user.dir"));
        System.out.println(content);
//        String aaPath =
        System.out.println(readFileByChars("src/main/java/com/roedeer/io/aa.txt"));
    }

    public static String readFileByBytes(String filePath){
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            return null;
        }

        StringBuffer content = new StringBuffer();

        try {
            byte[] temp = new byte[1024];
            FileInputStream fileInputStream = new FileInputStream(file);
            while(fileInputStream.read(temp) != -1){
                content.append(new String(temp));
                temp = new byte[1024];
            }

            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public static String readFileByChars(String filePath){
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            return null;
        }

        StringBuffer content = new StringBuffer();
        try {
            char[] temp = new char[1024];
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            while(inputStreamReader.read(temp) != -1){
                content.append(new String(temp));
                temp = new char[1024];
            }

            fileInputStream.close();
            inputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }


}
