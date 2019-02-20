package com.roedeer.netty.start.simpleDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by U6071369 on 10/21/2018.
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1){
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + "");
        }
        int port = Integer.parseInt(args[0]);   //设置端口值,（如果端口参数的格式不正确，则抛出一个NumberFormatException）
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)          //指定所使用的NIO传输channel
                    .localAddress(new InetSocketAddress(port))      //使用指定的端口设置套接字地址
                    .childHandler(new ChannelInitializer() {        //添加一个EchoServerHandler到子Channel的ChannelPipeline
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture f = b.bind().sync();                  //异步的绑定服务器,调用sync()方法阻塞等待直到绑定完成
            f.channel().closeFuture().sync();                   //获取Channel的closeFuture,并且阻塞当前线程直到他完成
        } finally {
            group.shutdownGracefully().sync();      //关闭EventLoopGroup,释放所有的资源
        }
    }
}
