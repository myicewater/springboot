package com.jaxon.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class RmqProducer {


    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = MqUtil.getConnection();
        String qName = "testquene";
        //mq提供Channel来将处理消息
        //创建Channel
        Channel channel = connection.createChannel();
        String msg = "hello world";
        //basicPublish将消息发送到指定的交换机
        channel.queueDeclare(qName,true,false,false,null);
        try{
            while(true){

                channel.basicPublish("", qName, null, UUID.randomUUID().toString().getBytes());
                Thread.sleep(500);
            }
        }catch(Exception e){
            //关闭连接
            channel.close();
            connection.close();
        }


    }
}
