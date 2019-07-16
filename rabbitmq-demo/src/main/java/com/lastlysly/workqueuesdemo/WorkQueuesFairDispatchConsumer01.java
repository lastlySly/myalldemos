package com.lastlysly.workqueuesdemo;

import com.lastlysly.config.RabbitMqConnectionConfiguration;
import com.rabbitmq.client.*;

import java.io.IOException;


/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-23 10:49
 *
 * 工作队列 公平分发 Fair dispatch
 *
 * 生产者
 **/
public class WorkQueuesFairDispatchConsumer01 {

    private final static String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws Exception {
// 获取到连接以及mq通道
        Connection connection = RabbitMqConnectionConfiguration.getConnection();
        final Channel channel = connection.createChannel();
// 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);//保证一次只分发一个
//定义一个消息的消费者
        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println(" [1] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }

        };
        boolean autoAck = false; //手动确认消息(自动应答改为false为手动)
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
    private static void doWork(String task) throws InterruptedException {
        Thread.sleep(1000);
    }
}
