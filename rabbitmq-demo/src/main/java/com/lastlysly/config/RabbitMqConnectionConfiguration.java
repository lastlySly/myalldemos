package com.lastlysly.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-22 16:39
 **/
public class RabbitMqConnectionConfiguration {


    /**
     * 获取mq连接
     * @return
     */
    public static Connection getConnection() throws IOException, TimeoutException {

        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置服务地址
        factory.setHost("127.0.0.1");

        //设置端口  AMQP 5672
        factory.setPort(5672);

        //设置库
        factory.setVirtualHost("/test_vh");

        //用户名
        factory.setUsername("lastlysly");

        //密码
        factory.setPassword("123456");

        return factory.newConnection();

    }

}
