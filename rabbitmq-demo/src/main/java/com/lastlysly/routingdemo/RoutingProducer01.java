package com.lastlysly.routingdemo;

import com.lastlysly.config.RabbitMqConnectionConfiguration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-23 14:01
 **/
public class RoutingProducer01 {

    private final static String EXCHANGE_NAME = "test_exchange_direct";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = RabbitMqConnectionConfiguration.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 消息内容
        String message = "id=1001的商品删除了";
//        channel.basicPublish(转换器名, 路由键, null, message.getBytes());
//        String routingKey = "add";
        String routingKey = "delete";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }

}
