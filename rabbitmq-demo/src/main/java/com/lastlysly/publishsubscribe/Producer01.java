package com.lastlysly.publishsubscribe;

import com.lastlysly.config.RabbitMqConnectionConfiguration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-23 11:47
 *
 * 订阅模式。将消息发送到交换机，队列与交换机绑定
 **/
public class Producer01 {

    private final static String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = RabbitMqConnectionConfiguration.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange 交换机 转发器
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); //fanout 分发，不处理路由键，只需要将队列绑定到交换机上。发送消息到交换机都会被转发到与该交换机绑定的所有队列
        // 消息内容
        String message = "Hello PB";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
