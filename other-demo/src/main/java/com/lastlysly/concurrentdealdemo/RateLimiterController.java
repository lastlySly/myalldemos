package com.lastlysly.concurrentdealdemo;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.lastlysly.utils.RedisLockUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-08-14 17:57
 * <p>
 * 抢购场景限流
 **/
@Controller
@RequestMapping("/rateLimiterController")
public class RateLimiterController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    RateLimiter rateLimiter = RateLimiter.create(10);
    private static AtomicInteger totalNum = new AtomicInteger(50);
    private static List<String> joinUserIds = Lists.newLinkedList();
    private static volatile Map<String, String> joinUserIdsMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        String age = redisTemplate.opsForValue().get("age");
        System.out.println(age);
        return "hello RateLimiter";
    }


    @GetMapping("/miaosha/{userId}")
    @ResponseBody
    public String miaosha(@PathVariable(value = "userId") String userId) {
        String redisLockKey = "redisLock:" + userId;
        synchronized(redisLockKey.intern()) {
            if (joinUserIdsMap.containsKey(userId)) {
                System.out.println("用户：" + userId + "，您已参与过秒杀，无法再次参与");
                return "用户：" + userId + "，您已参与过秒杀，无法再次参与";
            }
            System.out.println("等待时间：" + rateLimiter.acquire());
            if (rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
                System.out.println("用户：" + userId + "系统判断您在1秒内就能抢购到，请耐心等待");
            } else {
                System.out.println("用户：" + userId + "系统判断您在1秒内无法得到响应，短期内无法获取令牌，别排队了，待会再来吧");
                return "用户：" + userId + "系统判断您在1秒内无法得到响应，短期内无法获取令牌，别排队了，待会再来吧";
            }
            if (totalNum.get() > 0) {
                joinUserIdsMap.put(userId, userId);
                totalNum.getAndDecrement();
                System.out.println("用户：" + userId + "  参与秒杀成功，购买成功,当前剩余" + totalNum.get());
                return "用户：" + userId + "  参与秒杀成功，购买成功,当前剩余" + totalNum.get();
            } else {
                System.out.println("用户：" + userId + ",秒杀商品已售毕，购买失败");
                return "用户：" + userId + "，秒杀商品已售毕，购买失败";
            }
        }

    }

    @GetMapping("/miaosha2/{userId}")
    @ResponseBody
    public String miaosha2(@PathVariable(value = "userId") String userId) {
        String redisLockKey = userId;
        String requestId = UUID.randomUUID().toString();
        /**
         * requestId，保证谁加锁，谁解锁，防止一个请求进来加了锁，另一个进来解掉锁。因为都会走finally 的解锁操作。。
         */
        try{
//            redisTemplate,
            boolean lock = RedisLockUtils.lock(redisLockKey, requestId, 10);
            if (lock) {
                if (joinUserIdsMap.containsKey(userId)){
                    System.out.println("用户" + userId + "已购买，不能重复购买。");
                    return "用户" + userId + "已购买，不能重复购买。";
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                joinUserIdsMap.put(userId,userId);
                System.out.println("用户" + userId + "购买成功");
                return "用户" + userId + "购买成功";
            }
            System.out.println("用户" + userId + "没有获取到锁");
            return "没有获取到锁";
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisLockUtils.releaseLock(redisTemplate, redisLockKey, requestId);
            return "用户" + userId + "解锁";
        }
    }

    @GetMapping("/addGoods/{num}")
    @ResponseBody
    public String addGoodsStock(@PathVariable(value = "num") int num) {
        totalNum.getAndAdd(num);
        return "库存增加成功，当前库存:" + totalNum.get();
    }

    private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    @GetMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request) throws InterruptedException {
        if (threadLocal.get() == null) {
            threadLocal.set(1);
        }
        Integer n = threadLocal.get() + 1;
        TimeUnit.SECONDS.sleep(20);
        System.out.println(n);
        threadLocal.set(n);
        return "chengg";
    }
    @GetMapping("/test2")
    @ResponseBody
    public String test2(HttpServletRequest request) throws InterruptedException {
        if (threadLocal.get() == null) {
            threadLocal.set(1);
        }
        Integer n = threadLocal.get() + 1;
        System.out.println(n);
        threadLocal.set(n);
        return "chengg";
    }
}
