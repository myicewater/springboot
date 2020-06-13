package com.jaxon.demo.nio;

import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOSocketServer {

    public void nioSocket()throws  Exception{
        ServerSocketChannel serverSocketChannel =  ServerSocketChannel.open();
        SocketChannel accept = serverSocketChannel.accept();
        Selector selector =Selector.open();
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",8081));
        serverSocketChannel.configureBlocking(false);


        serverSocketChannel.register(selector,0);

    }
}
