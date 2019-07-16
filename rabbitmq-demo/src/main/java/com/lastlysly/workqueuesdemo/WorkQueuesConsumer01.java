package com.lastlysly.workqueuesdemo;

import com.lastlysly.config.RabbitMqConnectionConfiguration;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-23 10:07
 * 工作队列  Round-robin（轮询分发）
 * 一个生产者多个消费者
 * 消费者1
 **/
public class WorkQueuesConsumer01 {
    private static String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = RabbitMqConnectionConfiguration.getConnection();
        //获取channel
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){
            //消息到达触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);

                String msgString= new String(body,"utf-8");

                System.out.println("消费者【1】：" + msgString);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("消费者【1】done");
                }
            }
        };

        boolean autoAck = true; //自动应答
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);

    }

}
