package com.lastlysly.atomicdemo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-07-11 16:54
 **/
public class Demo02 {

    private String sTtdd;

    public static void main(String[] args) {
        final AtomicInteger a = new AtomicInteger(1);

        System.out.println(a.incrementAndGet());
        System.out.println(a);
        System.out.println(Demo01.count.incrementAndGet());
        System.out.println(Demo01.count);
    }

}
