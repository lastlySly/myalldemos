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
public class WorkQueuesFairDispatchConsumer02 {

    private final static String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws Exception {
// 获取到连接以及mq通道
        Connection connection = RabbitMqConnectionConfiguration.getConnection();
        final Channel channel = connection.createChannel();
// 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);//保证一次只分发一个
//定义一个消息的消费者
        final Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println(" [2] Received '" + message + "'");
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
        boolean autoAck = false; //关闭自动 确认
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }

    private static void doWork(String task) throws InterruptedException {
        Thread.sleep(2000);
    }

//    public static void oldAPi() throws Exception, TimeoutException {
//// 获取到连接以及mq通道
//        Connection connection = RabbitMqConnectionConfiguration.getConnection();
//        Channel channel = connection.createChannel();
//// 声明队列
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//// 同一时刻服务器只会发一条消息给消费者
//        channel.basicQos(1);
//// 定义队列的消费者
//        QueueingConsumer consumer = new QueueingConsumer(channel);
//// 监听队列，手动返回完成状态
//        channel.basicConsume(QUEUE_NAME, false, consumer);
//        // 获取消息
//        while (true) {
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            String message = new String(delivery.getBody());
//            System.out.println(" [x] Received '" + message + "'");
//// 休眠1秒
//            Thread.sleep(1000);
//            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//        }
//    }


}
