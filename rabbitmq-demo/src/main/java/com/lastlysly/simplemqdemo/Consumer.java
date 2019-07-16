package com.lastlysly.simplemqdemo;

import com.lastlysly.config.RabbitMqConnectionConfiguration;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-23 08:55
 *
 * 简单队列
 * 一个生产者一个消费者
 * 消费者  接收者
 **/
public class Consumer {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {



        //获取连接
        Connection connection = RabbitMqConnectionConfiguration.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        //旧版本3.4  定义消费者======================================================================================
//        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
//        channel.basicConsume(QUEUE_NAME,true,consumer);
//        while(true){
//
//            Delivery delivery = consumer.nextDelivery();
//            String msgString = new String(delivery.getBody());
//            System.out.println("[recv]msg:"+msgString);
//
//        }
        //==================================================================================================================


        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //定义消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);

                String msgString = new String(body,"utf-8");
                System.out.println("new api recv:" + msgString);;

            }
        };

        //监听队列
//        channel.basicConsume(队列名,消息应答,消费者);
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);




    }


}
