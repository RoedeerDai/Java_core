package com.roedeer.concurrent.threadCommunication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @Description
 * Java虽然说是基于内存通信的,但也可以使用管道通信
 * 需要注意的是,输入流和输出流需要先建立连接,这样线程 B 才能接收到线程 A 发送的消息
 *
 * @Author Roedeer
 * @Date 12/18/2018 5:16 PM
 **/
public class PipedMethod {

    private static final Logger logger = LoggerFactory.getLogger(PipedMethod.class);

    public static void main(String[] args) throws IOException {
        piped();
    }

    public static void piped() throws IOException {
        //面向于字符 PipedInputStream 面向于字节
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();

        //输入输出流建立连接
        writer.connect(reader);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("running");
                try {
                    for (int i = 0; i < 10; i++) {
                        writer.write(i + "");
                        Thread.sleep(1000);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("running2");
                int msg = 0;
                try {
                    while ((msg = reader.read()) != -1) {
                        logger.info("msg={}", (char) msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }

}
