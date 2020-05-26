package com.example.demo.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketClient {

    public void client() throws  Exception{
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;
        try{

             socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8081));

            if(socketChannel.finishConnect()){
                int i=0;
                while(true){
                    Thread.sleep(1000);
                    String info = "this is "+i+++"message from client";
                    byteBuffer.clear();
                    byteBuffer.put(info.getBytes());
                    byteBuffer.flip();
                    while(byteBuffer.hasRemaining()){

                        socketChannel.write(byteBuffer);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                socketChannel.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SocketClient socketClient = new SocketClient();
        socketClient.client();
    }
}
