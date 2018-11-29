package com.roedeer.netty.nettyDev;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


/**
 * Created by U6071369 on 11/3/2018.
 * Netty的write方法并不直接将消息写到SocketChannel中,调用write方法只是把待发送的消息发送到缓冲数组中
 * 通过调用flush方法,将缓冲数组中消息全部写到SocketChannel中
 */
public class NettyTimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8");
        System.out.println("The time server receive order : " + body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new
                java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
