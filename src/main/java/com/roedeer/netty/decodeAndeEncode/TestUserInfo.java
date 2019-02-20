package com.roedeer.netty.decodeAndeEncode;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @Description
 * @Author Roedeer
 * @Date 1/5/2019 1:09 PM
 **/
public class TestUserInfo {

    /**
     * jdk序列化机制编码后二进制数组大小是二进制编码的5倍多
     */
    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("Welcome to Netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The jdk serializable length is : " + b.length);
        bos.close();
        System.out.println("-------------------------------------------");
        System.out.println("The byte array serializable length is : " + info.codeC().length);
    }

    /**
     * Java序列化和二进制编码进行性能测试,编码100万次,统计耗费的总时间  jdk 1975ms  二进制 169ms
     * 无论是编码的码流大小,还是序列化的性能,JDK默认的序列化机制都表现差
     * 可以选择Google的Protobuf--Google Protocol Buffers框架
     * FaceBook的Thrift框架
     * JBoss Marshalling
     * @throws IOException
     */
    @Test
    public void testPerformUserInfo() throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("Welcome to Netty");
        int loop = 1000000;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(info);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("The jdk serializable cost time is : " + (endTime - startTime) + " ms");
        bos.close();
        System.out.println("-------------------------------------------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] b = info.codeC(buffer);
        }
        endTime = System.currentTimeMillis();
        System.out.println("The byte array serializable cost time is : " + (endTime - startTime) + " ms");
    }

}
