package com.lastlysly.demo;

import org.springframework.util.Assert;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-02-27 10:53
 **/
public class SpringAssertDemo {

    public static void main(String[] args) {

        boolean isOk = false;
        assert isOk = true;
        System.out.println("断言开启情况：" + isOk);

        Assert.state(3>0,"测试");
        System.out.println(1111);
        Assert.state(0>3,"测试2");

        System.out.println(2333);
    }

}
