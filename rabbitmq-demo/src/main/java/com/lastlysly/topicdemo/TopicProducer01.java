package com.lastlysly.topicdemo;

import com.lastlysly.config.RabbitMqConnectionConfiguration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-28 09:06
 **/
public class TopicProducer01 {
    private final static String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = RabbitMqConnectionConfiguration.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 消息内容
        String message = "商品......";
//        channel.basicPublish(转换器名, 路由键, null, message.getBytes());
//        String routingKey = "add";
        String routingKey = "goods.add";
//        String routingKey2 = "goods.add";
        for (int i = 0; i< 10; i++) {
            String msg = message;
            msg += i;
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        }
//        channel.basicPublish(EXCHANGE_NAME, routingKey2, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
