package com.lastlysly.workqueuesdemo;

import com.lastlysly.config.RabbitMqConnectionConfiguration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-23 10:01
 *
 * 工作队列  Round-robin（轮询分发）
 * 一个生产者多个消费者
 *生产者
 **/
public class WorkQueuesProducer {

    private static String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = RabbitMqConnectionConfiguration.getConnection();

        //获取channel
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for (int i = 0; i < 50; i++){
            String msg = "hello" + i;
            System.out.println("工作队列生产者 send：" + msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

            Thread.sleep(i*20);
        }

        channel.close();
        connection.close();

    }
}
