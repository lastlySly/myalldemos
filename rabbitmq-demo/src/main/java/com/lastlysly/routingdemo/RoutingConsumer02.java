package com.lastlysly.routingdemo;

import com.lastlysly.config.RabbitMqConnectionConfiguration;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-23 14:00
 **/
public class RoutingConsumer02 {

    private final static String EXCHANGE_NAME = "test_exchange_direct";
    private final static String QUEUE_NAME = "test_queue_direct_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取到连接以及mq通道
        Connection connection = RabbitMqConnectionConfiguration.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);


        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"delete");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"add");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"update");

        channel.basicQos(1);
        // 定义一个消费者
        Consumer consumer = new DefaultConsumer(channel) {
            // 消息到达 触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("[2] Recv msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] done ");
// 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }

        };
        boolean autoAck = false;//自动应答false，即：手动应答
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
