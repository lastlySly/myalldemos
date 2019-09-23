package com.lastlysly.concurrentdealdemo;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-08-14 17:57
 *
 *  抢购场景限流
 **/
@Controller
@RequestMapping("/rateLimiterController")
public class RateLimiterController {

    RateLimiter rateLimiter = RateLimiter.create(10);
    private static AtomicInteger totalNum = new AtomicInteger(50);
    private static List<String> joinUserIds = Lists.newLinkedList();
    private static ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>();

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "hello RateLimiter";
    }


    @GetMapping("/miaosha/{userId}")
    @ResponseBody
    public String miaosha(@PathVariable(value="userId") String userId){

//        synchronized (userId.intern()){
            if (joinUserIds.contains(userId)){
                System.out.println("用户：" + userId + "，您已参与过秒杀，无法再次参与");
                return "用户：" + userId + "，您已参与过秒杀，无法再次参与";
            }
            System.out.println("等待时间："+rateLimiter.acquire());


            if (rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)){
                System.out.println("系统判断您在1秒内就能抢购到，请耐心等待");
            }else{
                System.out.println("系统判断您在1秒内无法得到响应，短期内无法获取令牌，别排队了，待会再来吧");
                return "系统判断您在1秒内无法得到响应，短期内无法获取令牌，别排队了，待会再来吧";
            }

            if(totalNum.get() > 0){
                joinUserIds.add(userId);
                totalNum.getAndDecrement();
                System.out.println("用户：" + userId + "  参与秒杀成功，购买成功,当前剩余"+totalNum.get());
                return "用户：" + userId + "  参与秒杀成功，购买成功,当前剩余"+totalNum.get();
            }else{
                System.out.println("用户：" + userId + ",秒杀商品已售毕，购买失败");
                return "秒杀商品已售毕，购买失败";
            }

//        }

    }

    @GetMapping("/addGoods/{num}")
    @ResponseBody
    public String addGoodsStock(@PathVariable(value = "num") int num){
        totalNum.getAndAdd(num);
        return "库存增加成功，当前库存:"+totalNum.get();
    }
}
