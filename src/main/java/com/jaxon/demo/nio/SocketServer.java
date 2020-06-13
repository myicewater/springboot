package com.jaxon.demo.nio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public void server() throws  Exception{

        ServerSocket serverSocket = new ServerSocket(8081);
        byte[] bytes = new byte[1024];
        while(true){

            Socket accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();
            int read = inputStream.read(bytes);
            while (read != -1){
                for(int i=0;i<read;i++){
                    System.out.println((char)bytes[i]);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SocketServer socketServer = new SocketServer();
        socketServer.server();
    }
}
