package com.roedeer.netty.unPackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Description TCP粘包拆包
 * @Author Roedeer
 * @Date 1/5/2019 10:44 AM
 **/
public class TimeServerHandler extends ChannelHandlerAdapter {

    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     *
     * @param ctx
     * @param msg 删除回车换行符后的请求消息,不需要额外考虑处理读半包问题,也不需要对msg进行编码
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        // 请求消息删除回车换行符
//        String body = new String(req, "UTF-8").substring(0,
//                req.length - System.getProperty("line.separator").length());
        String body = (String) msg;
        System.out.println("The time server receive order:" + body + " ; the counter is : " + ++count);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
                System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }
}
