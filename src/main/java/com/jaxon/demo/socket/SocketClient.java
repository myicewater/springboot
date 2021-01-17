package com.jaxon.demo.socket;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class SocketClient {

    private String id = "789";

    public void clientHandle()throws  Exception{
        Socket socket = new Socket(InetAddress.getLocalHost(),8777);


        new Thread(new ClientRead(socket,id)).start();
        new Thread(new ClientWrite(socket,id)).start();
    }

    public static void main(String[] args) throws Exception {

        new SocketClient().clientHandle();


    }
}
