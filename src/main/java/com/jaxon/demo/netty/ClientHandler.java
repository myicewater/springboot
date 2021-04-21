package com.jaxon.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 这是被动处理
 */
public class ClientHandler  extends ChannelInboundHandlerAdapter {




    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端准备好了");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf =(ByteBuf)msg;
        byte[] content = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(content);
        String strC = new String(content,"utf-8");
        System.out.println("接收到服务端信息："+strC);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
