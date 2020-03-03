package com.lastlysly.multithreading;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-01-20 11:29
 **/
class HelloThread extends Thread {
    private String name;
    public HelloThread(String name){
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0;i < 3; i++) {
            System.out.println("Hello " + name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class HelloThreadMain {
    public static void main(String[] args) {
        Thread thread1 = new HelloThread("Bob");
        Thread thread2 = new HelloThread("lastly");
        thread1.start();
        thread2.start();
        for (int i = 0; i<3; i++) {
            System.out.println("Main");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
