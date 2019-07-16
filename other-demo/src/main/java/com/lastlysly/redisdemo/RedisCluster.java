package com.lastlysly.redisdemo;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-05-24 11:47
 * redis 集群
 **/
public class RedisCluster {

    public static void main(String[] args) throws IOException
    {
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("192.168.0.109", 8081));
        jedisClusterNode.add(new HostAndPort("192.168.0.109", 8082));
        jedisClusterNode.add(new HostAndPort("192.168.0.109", 8083));
        jedisClusterNode.add(new HostAndPort("192.168.0.109", 8084));
        jedisClusterNode.add(new HostAndPort("192.168.0.109", 8085));
        jedisClusterNode.add(new HostAndPort("192.168.0.109", 8086));

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(10);
        config.setTestOnBorrow(true);
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNode, 6000, 10, config);
        System.out.println(jedisCluster.set("student", "aaron"));
        System.out.println(jedisCluster.set("age", "18"));

        System.out.println(jedisCluster.get("student"));
        System.out.println(jedisCluster.get("age"));
        System.out.println(jedisCluster.get("name"));


        /**
         * 注意 redis3.0的集群，用jedis的JedisCluster.close()方法造成的集群连接关闭的情况。
         * jedisCluster内部使用了池化技术，每次使用完毕都会自动释放Jedis因此不需要关闭。
         * 如果调用close方法后再调用jedisCluster的api进行操作时就会出现如上错误。
         *
         * 不加的话程序运行完不会停止，加了会停止
         *
         */
        jedisCluster.close();

    }
}
