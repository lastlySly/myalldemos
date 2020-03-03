package com.lastlysly.multithreading.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-05-22 14:37
 **/
public class ForkJoinDemo2 {

    static class Test2 extends RecursiveTask<Integer> {
        private static final int THREAD_HOLD = 10;

        private int start;
        private int end;


        public Test2(int start, int end) {
            this.start = start;
            this.end = end;
        }

        /**
         * The main computation performed by this task.
         *
         * @return the result of the computation
         */
        @Override
        protected Integer compute() {
            int sum = 0;

            //如果任务足够小就计算
            boolean canCompute = (end - start) <= THREAD_HOLD;
            if(canCompute){
                for(int i=start;i<=end;i++){
                    sum += i;
                }
            }else{
                int middle = (start + end) / 2;
                Test2 left = new Test2(start,middle);
                Test2 right = new Test2(middle+1,end);
                //执行子任务
//                left.fork();
//                right.fork();
                invokeAll(left,right);
                //获取子任务结果
                int lResult = left.join();
                int rResult = right.join();
                sum = lResult + rResult;
            }
            return sum;
        }
    }


    public static void main(String[] args){

        Long start = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        Test2 task = new Test2(1,10000);
        Future<Integer> result = pool.submit(task);
        try {
            System.out.println("测试");
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Long end = System.currentTimeMillis();

        System.out.println("执行时间：" + (end - start));


    }

}
