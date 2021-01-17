package com.jaxon.demo.socket;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientWrite implements Runnable{

    private String id;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader inputReader;
    private Scanner scanner;

    public ClientWrite(Socket socket,String id) throws Exception {
        this.id = id;
        this.socket = socket;
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write(id+"\n");
        bufferedWriter.flush();
        scanner  = new Scanner(System.in);
        System.out.println("请输入消息...");
    }

    @Override
    public void run() {

        while(true){

            try {

                String next = null;
                String content = scanner.nextLine();
//                while(!"\n".equals(next = scanner.nextLine())){
//                    content+= next;
//                }

                if(StringUtils.isNotEmpty(content)){
//                    System.out.println("控制台输入："+content);
                    Message message = new Message(id,"123",content);

                    bufferedWriter.write(JSONObject.toJSONString(message) +"\n");
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                System.out.println("链接服务器异常！");
                e.printStackTrace();
                break;

            }

        }
    }
}
