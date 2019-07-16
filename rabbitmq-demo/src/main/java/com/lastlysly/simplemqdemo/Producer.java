package com.lastlysly.simplemqdemo;

import com.lastlysly.config.RabbitMqConnectionConfiguration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-22 16:47
 *
 * 简单队列
 * 一个生产者一个消费者
 * 生产者，发送者
 *
 **/
public class Producer {

    //队列名
    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

//        Logger logger = LoggerFactory.getLogger(this.getClass());

        //获取一个连接
        Connection connection = RabbitMqConnectionConfiguration.getConnection();

        //创建一个通道（频道）
        Channel channel = connection.createChannel();


        /*消息持久化我们不能直接将程序里面的 false 改成 true ,我们已经定义了一个名叫 test_queue_work 的未持久化的队
            列。RabbitMQ 不允许使用不同的参数设定重新定义已经存在的队列，并且会返回一个错误。
            一个快速的解决方案——就是声明一个不同名字的队列，比如 task_queue。或者我们登录控制台将队列删除就可
            以了*/
        //创建队列声明
//        channel.queueDeclare(队列名,是否消息持久化,false,false,null);
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello simple queue!2";

//      channel.basicPublish(交换机名,路由名（或队列）,null,消息内容);
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("--send msg" + msg);
        channel.close();
        connection.close();

    }

}
