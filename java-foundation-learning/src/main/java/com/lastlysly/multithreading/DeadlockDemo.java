package com.lastlysly.multithreading;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-01-20 15:03
 * 死锁测试
 **/
public class DeadlockDemo {
    public static void main(String[] args) throws InterruptedException {
        SharedObject sharedObject = new SharedObject();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    sharedObject.a2b();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    sharedObject.b2a();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("end");


    }
}

class SharedObject {
    final Object lockA = new Object();
    final Object lockB = new Object();

    public void a2b() {
        synchronized(lockA) {
            System.out.println("a2b获得lockA");
            synchronized (lockB) {
                System.out.println("a2b获得lockB");
            }
        }
    }
    public void b2a() {
        synchronized(lockB) {
            System.out.println("b2a获得lockB");
            synchronized (lockA) {
                System.out.println("b2a获得lockA");
            }
        }
    }
}
