package com.jaxon.demo.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.HashMap;
import java.util.Map;

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

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7098).syncUninterruptibly();

        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){


                        MsgPro m = new MsgPro();
                        Map map = new HashMap();
                        map.put("shopEntityId","1F3FD8MN7FHDE40AB2M1010JKL001AE3");
                        map.put("mac","BBBB10B6B1B2");
                        m.setData(map);
                        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(JSONObject.toJSONString(m).getBytes()));

                        channelFuture.channel().closeFuture().sync();
                        group.shutdownGracefully();

                }
            }
        });



    }
}
