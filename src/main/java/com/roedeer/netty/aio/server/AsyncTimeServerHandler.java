package com.roedeer.netty.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * Created by U6071369 on 11/3/2018.
 * 异步时间服务器类处理器
 * 实际的项目中并不需要独立的AsyncTimeServerHandler来处理AsynchronousServerSocketChannel,这里是demo演示
 */
public class AsyncTimeServerHandler implements Runnable {

    private int port;

    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    /**
     * 绑定端口,打印成功消息
     * @param port
     */
    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化CountDownLatch对象,作用是完成一组正在执行的操作之前,运行当前的线程一直阻塞
     * 此处主要是防止服务端执行完一次任务后直接退出
     */
    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 传递一个CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler>类型的handler实例
     * 接收accept操作成功的通知消息
     */
    public void doAccept() {
        asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
    }
}
