package com.jaxon.demo.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class RmqConsumer {

    public static void main(String[] args) throws Exception {
        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        String qName = "testquene";
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String content = new String(body,"UTF-8");
                System.out.println("msg:"+content);

                super.handleDelivery(consumerTag, envelope, properties, body);
            }
        };

        channel.basicConsume(qName,true,consumer);


    }
}
