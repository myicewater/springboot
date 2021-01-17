package com.jaxon.demo.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketServer {

    static Map<String,ClientHandler> clients = new ConcurrentHashMap<>();

    public void serverRun(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8777);
            System.out.println("服务器启动成功！");
        } catch (IOException e) {
            System.out.println("服务端启动异常!");
            e.printStackTrace();
        }
        while(true){
            Socket accept = null;
            ClientHandler clientHandler = null;
            try {
                accept = serverSocket.accept();
                clientHandler = new ClientHandler(accept);
                clients.put(clientHandler.getId(),clientHandler);
                new Thread(clientHandler).start();
            } catch (Exception e) {
                System.out.println("与客户端建立链接异常!");
                e.printStackTrace();
                if(clientHandler != null){
                    clientHandler.detroy();
                }
                if(accept != null){

                    try {
                        accept.close();
                    } catch (IOException ioException) {
                        System.out.println("连接关闭异常!");
                        ioException.printStackTrace();
                    }
                }
            }



        }

    }

    public static void main(String[] args) {

        new SocketServer().serverRun();

    }
}

