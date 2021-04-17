package com.jaxon.demo.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new ClientHandler());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).syncUninterruptibly();

        Message message = new Message("GET_ID","");
        String s = JSONObject.toJSONString(message);

        try {
            while(true){
                byte[] msg = "给我分配ID".getBytes();
                channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(msg));

                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            channelFuture.channel().closeFuture().sync();
            group.shutdownGracefully();
        }

    }
}
