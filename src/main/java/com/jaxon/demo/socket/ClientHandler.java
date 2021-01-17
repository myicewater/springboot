package com.jaxon.demo.socket;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.Socket;

class ClientHandler implements  Runnable{


    private String id;
    private Socket socket;
    private BufferedReader bufferedReader;

    private BufferedWriter bufferedWriter;

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        if(socket != null){
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String id = bufferedReader.readLine();//建立链接后，自报身份
            if(StringUtils.isNotEmpty(id)){
                this.id = id;
                System.out.println(id+" 上线了！");
            }else{
                throw new Exception("没获取到身份信息");
            }

        }else{
            throw new Exception("建立连接异常");
        }
    }

    /**
     * 销毁客户端
     */
    public void detroy(){
        if(bufferedReader != null ){
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(bufferedWriter != null){
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(socket != null ){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @param clientHandler
     */
    public void sendMsg(ClientHandler clientHandler,Message message){

        String s = JSONObject.toJSONString(message);
        try {
            BufferedWriter bufferedWriter = clientHandler.getBufferedWriter();
            bufferedWriter.write(s+"\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(s);
            e.printStackTrace();
            SocketServer.clients.remove(clientHandler.getId());
            clientHandler.detroy();
        }
    }



    @Override
    public void run() {
        while(true){

            try{
                if(socket.isConnected()){
                    String s = bufferedReader.readLine();
                    if(StringUtils.isNotEmpty(s)){
//                        System.out.println(id+"："+s);

                        Message message = JSONObject.parseObject(s,Message.class);
                        if(message != null && StringUtils.isNotEmpty(message.getToId())){
                            System.out.println(id+"对"+message.getToId()+"说："+message.getContent());
                            ClientHandler clientHandler = SocketServer.clients.get(message.getToId());
                            if(clientHandler == null){//对方不在线
                                message.setContent(message.getToId() +"当前不在线！");
                                message.setToId(message.getFromId());
                                message.setFromId("系统");
                                sendMsg(this,message);
                            }else{
                                sendMsg(clientHandler,message);
                            }

                        }else{
                            System.out.println(id+"的消息无法解析！");
                        }


                    }
                }else{
                    System.out.println(id+" 下线了！");
                    SocketServer.clients.remove(id);
                    break;
                }
            }catch (IOException e){
                e.printStackTrace();
                System.out.println(id+" 下线了！");
                SocketServer.clients.remove(id);
                break;
            }

        }
    }


    public String getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
}