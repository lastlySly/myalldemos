package com.lastlysly.publishsubscribe;

import com.lastlysly.config.RabbitMqConnectionConfiguration;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-23 11:49
 **/
public class Consumer01 {

    private final static String QUEUE_NAME = "test_queue_fanout_email";
    private final static String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] argv) throws Exception {
// 获取到连接以及mq通道
        Connection connection = RabbitMqConnectionConfiguration.getConnection();
        final Channel channel = connection.createChannel();
// 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
// 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
//------------下面逻辑和work模式一样-----
// 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        // 定义一个消费者
        Consumer consumer = new DefaultConsumer(channel) {
            // 消息到达 触发这个方法

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("[1] Recv msg:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] done ");
// 手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }


}
