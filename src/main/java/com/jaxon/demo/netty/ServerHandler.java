package com.jaxon.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 这是被动处理
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static Map<ChannelHandlerContext,Integer> connections = new ConcurrentHashMap<ChannelHandlerContext,Integer>();
    

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channel active..");
    }

    public Integer getServiceId(ChannelHandlerContext ctx){

        if(connections.containsKey(ctx)){
            return connections.get(ctx);
        }
        Collection<Integer> values = connections.values();
        int result = 0;
        for(int i=1; i<10;i++){
            if(!values.contains(i)){
                final Integer integer = connections.putIfAbsent(ctx, i);
                result = i;
                break;
            }
        }
        return result;
    }

    public void removeServiceId(ChannelHandlerContext ctx){
        if(connections.containsKey(ctx)){
            connections.remove(ctx);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] content = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(content);
        String con = new String(content,"UTF-8");
        System.out.println("server:"+con);

        Integer serviceId = getServiceId(ctx);
        System.out.println("返回给客户端服务id："+serviceId);
        String respons = ""+serviceId;
        ChannelFuture channelFuture = ctx.writeAndFlush(Unpooled.copiedBuffer(respons.getBytes()));
        channelFuture.isSuccess();

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server 读完了");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("链接异常了");
        removeServiceId(ctx);
        ctx.close();
    }
}
