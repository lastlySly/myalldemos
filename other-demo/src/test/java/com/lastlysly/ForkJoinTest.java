package com.lastlysly;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-05-23 11:37
 **/
public class ForkJoinTest {
    /**************************************  并行流 与 顺序流  ******************************************************/

    /**
     *并行流 与 顺序流
     */
    @Test
    public void test03() {

        Instant start = Instant.now();
        Long l = LongStream.rangeClosed( 0,110 )
                //并行流
                .parallel()
                .reduce( 0,Long::sum );

        System.out.println(l);


        LongStream.rangeClosed( 0,110 )
                //顺序流
                .sequential()
                .reduce( 0,Long::sum );


        Instant end = Instant.now();
        System.out.println("耗费时间"+ Duration.between( start,end ).toMillis());

    }
}
