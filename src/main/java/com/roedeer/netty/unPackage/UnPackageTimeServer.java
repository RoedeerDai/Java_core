package com.roedeer.netty.unPackage;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Description TimeClientHandler和TimeServerHandler发送消息出现TCP的粘包拆包问题
 *              本应该发送100条查询时间指令,client收到100条回复,最终结果只是两条BAD ORDER,由于消息粘连在一起了
 *
 *              Netty使用LineBasedFrameDecoder解决TCP粘包问题
 *              原理就是遍历ByteBuf中的可读字节,以换行为结束标志
 *              Netty使用LineBasedFrameDecoder+StringDecoder组合就是按行切换的文本解码器
 * @Author Roedeer
 * @Date 1/5/2019 11:13 AM
 **/
public class UnPackageTimeServer {

    public void bind(int port) throws Exception {
        // 配置服务端的 NIO 线程组,一个用于服务端接受客户端的连接
        // 一个用于进行SocketChannel的网络读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建ServerBootstrap对象,Netty用于启动NIO服务端的辅助启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            // 绑定端口,同步等待成功,sync--同步阻塞
            ChannelFuture future = bootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅退出,释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 新增两个解码器LineBasedFrameDecoder,StringDecoder
     */
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
//            socketChannel.pipeline().addLast(new TimeServerHandler());
            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8888;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // use default num
            }
        }
        new UnPackageTimeServer().bind(port);
    }
}
