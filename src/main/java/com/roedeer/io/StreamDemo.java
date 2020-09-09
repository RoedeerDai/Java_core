package com.roedeer.io;

import org.junit.Test;

import java.io.*;

/**
 * Created by U6071369 on 8/9/2018.
 */
public class StreamDemo {

    /**
     * java使用了一种灵巧的机制来分离这两种职责。
     * 某些流（例如FileInputStream和由URL类的openStream方法返回的输入流）可以从文件和其他更外部的位置获取字节
     * 而其他的流(例如DataInputStream和PrintWriter)可以将字节组装到更有用的数据类型中
     * 使用的时候必须对二者进行组合
     * @throws IOException
     */
    public void stream() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("employee.dat");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        double s = dataInputStream.readDouble();
    }

    @Test
    public void printWriter() throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("/employee.txt"));
        String name = "Harry Hacker";
        double salary = 75000;
        out.print(name);
        out.print(' ');
        out.println(salary);
    }

    @Test
    public void fileWriter() throws IOException {
        try{

            String content = "A cat will append to the end of the file";
            File file =new File("EquitiesDerivativesAndFundsQuote.Instrument.All.3.2019-12-20-0245.2019-12-20-1048.Incremental.1.of.1.xml");
            if(!file.exists()){
                file.createNewFile();
            }
            //使用true，即进行append file
            FileWriter fileWritter = new FileWriter(file.getName(),true);
            fileWritter.write(content);
            fileWritter.close();
            System.out.println("finish");
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String property = System.getProperty("user.dir");
        System.out.println(property);
        System.out.println(File.separator);
    }
}
