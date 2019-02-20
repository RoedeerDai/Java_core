package com.roedeer.netty.start.nio.traditional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by U6071369 on 7/26/2018.
 */
public class FileChannelDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = null;
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("log4j.properties").getPath();
            aFile = new RandomAccessFile(path,"rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(aFile);
        FileChannel fileChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int byteRead = 0;
        try {
            byteRead = fileChannel.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (byteRead != -1) {
            System.out.println("Read: " + byteRead);
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.println((char)buf.get());
            }

            buf.clear();
            byteRead = fileChannel.read(buf);
        }
        fileChannel.close();
    }
}
