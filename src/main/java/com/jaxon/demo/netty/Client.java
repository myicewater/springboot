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
import java.util.UUID;

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

        for(int i=0;i<5;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ChannelFuture channelFuture = bootstrap.connect("tcpsdrmall.dev.goago.cn", 10112).syncUninterruptibly();
                    channelFuture.addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if(channelFuture.isSuccess()){


                                MsgPro m = new MsgPro();
                                Map map = new HashMap();
                                map.put("shopEntityId","1F3FD8MN7FHDE40AB2M1010JKL001AE3");
                                map.put("mac", UUID.randomUUID().toString().replaceAll("-","").toUpperCase());
                                m.setData(map);
                                m.setOperCode("DEVICE_AUTH");
                                channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(JSONObject.toJSONString(m).getBytes()));

                            }
                        }
                    });

                    try {
                        channelFuture.channel().closeFuture().sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //group.shutdownGracefully();//关闭线程池并释放所有资源
                }
            }).start();

        }



    }
}
