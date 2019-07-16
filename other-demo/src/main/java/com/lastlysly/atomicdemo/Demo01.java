package com.lastlysly.atomicdemo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-07-11 09:38
 **/
public class Demo01 {

    private static final int THREADS_CONUT = 20;
    public static final AtomicInteger count = new AtomicInteger(0);

    public static void increase() {
        count.incrementAndGet();
    }

    public static void main(String[] args) {
        System.out.println(2333);
        Thread[] threads = new Thread[THREADS_CONUT];
        for (int i = 0; i < THREADS_CONUT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(count);
    }
}
