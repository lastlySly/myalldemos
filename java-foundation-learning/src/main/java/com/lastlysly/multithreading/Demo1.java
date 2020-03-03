package com.lastlysly.multithreading;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-01-20 11:17
 **/
public class Demo1 {

    public static void main(String[] args) {
        double ss = 1.579595136E9;
        double s = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println(s-ss);
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        ses.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println(2333);
            }
        }, 2, 3, TimeUnit.SECONDS);
//        Thread thread1 = new Thread(new MyThread());
//        try {
//            System.out.println("start");
//            thread1.start();
//            thread1.join();// 等待线程thread1执行结束
//            System.out.println("end");
//            System.out.println("一个线程可以等待另一个线程直到其运行结束。即 thread.join() 用法。这里主线程等待MyThread运行完才运行以下的");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}

class MyThread implements Runnable{

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        for (int i = 0; i<3;i++){
            System.out.println("hello");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
