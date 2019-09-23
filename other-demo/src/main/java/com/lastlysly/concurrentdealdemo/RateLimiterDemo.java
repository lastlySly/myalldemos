package com.lastlysly.concurrentdealdemo;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-08-14 15:47
 *
 * 使用RateLimiter完成简单的大流量限流，抢购秒杀限流
 **/
public class RateLimiterDemo {



    public static void main(String[] args) {

        int corePoolSize = 5;
        int maximumPoolSize = 10;
        /**
         * 保持空闲时间
         */
        long keepAliveTime = 20;
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(50);

        RejectedExecutionHandler myRejectedExecutionHandler = new MyRejectedExecutionHandler();

        ThreadFactory myThreadFactory = new MyThreadFactory();

        /**
         * 每秒执行的任务个数
         */
        double runSpeed = 10;
        RateLimiter rateLimiter = RateLimiter.create(runSpeed);

        List<Runnable> tasks = Lists.newArrayList();
        for (int i = 0; i < 100; i++){
            tasks.add(new UserRequest(i));
        }

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,
                TimeUnit.SECONDS,blockingQueue,myThreadFactory,myRejectedExecutionHandler);

        for (Runnable runnable : tasks){
            System.out.println("等待时间：" + rateLimiter.acquire());
            threadPool.execute(runnable);
        }




    }
}

class UserRequest implements Runnable{

    private int id;

    public UserRequest(int id){
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("================="+id);
    }
}

/**
 * 自定义拒绝策略
 */
class MyRejectedExecutionHandler implements RejectedExecutionHandler{

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        //处理任务被拒绝，记录日志等
        Logger logger = LoggerFactory.getLogger(MyRejectedExecutionHandler.class);
        logger.info("进入自定义拒绝策略：{}",r.getClass().getName());
    }
}

/**
 * 自定义ThreadFactory
 * 线程工厂
 */
class MyThreadFactory implements ThreadFactory{

    private AtomicInteger myThreadNum = new AtomicInteger(1);
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r,"my-thread-" + myThreadNum.getAndIncrement());
        System.out.println("线程：" + thread.getName() + "已经创建");
        return thread;
    }
}
