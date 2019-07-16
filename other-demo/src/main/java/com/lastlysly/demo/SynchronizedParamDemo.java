package com.lastlysly.demo;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-02-27 09:04
 **/
public class SynchronizedParamDemo extends Thread {


    private String param;

    public SynchronizedParamDemo(String pa){
        param=pa;
    }

    private static int index=0;

    public static void main(String[] args) {
        int i = 100;
        for (int i1 = 0; i1 < i; i1++) {
            new SynchronizedParamDemo("param").start();
        }
    }

    @Override
    public void run(){
        /**
         * 测试一：正常情况，并发
         */
//        System.out.println(param+index);
//        index++;


        /**
         * 测试二：
         * 锁住变量param  如果param相同，那么就会排队。需要等里面业务执行完才走下个
         *
         * 该方法jdk7以上不推荐
         *
         * 这个在jdk6里问题不算大,因为String.intern()会在perm里产生空间,如果perm空间够用的话,这个不会导致频繁Full GC,
         * 但是在jdk7里问题就大了,String.intern()会在heap里产生空间,而且还是老年代,如果对象一多就会导致Full GC时间超长!!!
         **/
//        synchronized (param.intern()){
//            System.out.println(param+index);
//            /**
//             * 这里面处理业务
//             */
//            index++;
//        }

        /**
         * 引用google-guava包。解决测试二问题
         */
        Interner<String> pool = Interners.newWeakInterner();
        synchronized ( pool.intern(param)){
            System.out.println(param+index);
            index++;
        }

    }

}
