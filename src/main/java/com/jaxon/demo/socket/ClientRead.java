package com.jaxon.demo.socket;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientRead  implements Runnable{

    private String id;
    private Socket socket;
    private BufferedReader bufferedReader;
    private static boolean status = true;


    public ClientRead(Socket socket,String id) throws  Exception {
        this.id = id;
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        while(true){

            if(socket.isConnected()){
                try{
                    String s = bufferedReader.readLine();
                    if(StringUtils.isNotEmpty( s)){
                        Message message = JSONObject.parseObject(s,Message.class);
                        if(message != null){

                            System.out.println(message.getFromId()+":"+message.getContent());
                        }
                    }

                }catch (Exception e){
                    System.out.println("链接服务器异常！");
                    e.printStackTrace();
                    break;
                }

            }else{
                status = false;
            }

        }

    }
}
