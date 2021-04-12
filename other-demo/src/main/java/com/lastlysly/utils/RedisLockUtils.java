package com.lastlysly.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Objects;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-03-31 10:32
 **/
@Component
public class RedisLockUtils {

    private static StringRedisTemplate redisTemplate;

    /**
     * springboot加载，放入ioc容器调用后
     * @param redisTemplate1
     */
    public RedisLockUtils(StringRedisTemplate redisTemplate1) {
        this.redisTemplate = redisTemplate1;
    }

    private static String lockScript = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then  return redis.call('expire',KEYS[1],ARGV[2])  else return 0 end";

    private static String releaseLockScript = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
//StringRedisTemplate redisTemplate,
    public static boolean lock(String key, String requestId, int expiresTime) {

        RedisScript<Long> redisScript = new DefaultRedisScript<>(lockScript, Long.class);
        RedisSerializer strSerializer = new StringRedisSerializer();
        Object result = redisTemplate.execute(redisScript, strSerializer, strSerializer, Collections.singletonList(key), requestId, String.valueOf(expiresTime));
        Long SUCCESS = 1L;
        return SUCCESS.equals(result);
    }

    public static boolean releaseLock(StringRedisTemplate redisTemplate, String key, String requestId) {
        RedisScript<Long> redisScript = new DefaultRedisScript<>(releaseLockScript, Long.class);
        RedisSerializer strSerializer = new StringRedisSerializer();
        Object result = redisTemplate.execute(redisScript, strSerializer, strSerializer, Collections.singletonList(key), requestId);
        Long SUCCESS = 1L;
        return SUCCESS.equals(result);
    }

    /**
     * 获取锁
     */
    @Deprecated
    public static boolean lock(StringRedisTemplate redisTemplate, String key, int expireSec) {
        String lock = "redis_lock:key_" + key;
        Charset utf8 = StandardCharsets.UTF_8;
        byte[] lock_key = lock.getBytes(utf8);

        final int LOCK_EXPIRE = expireSec * 1000;

        final int LOCK_TTL = expireSec + 2; // s 秒

        RedisCallback<Boolean> redisCallback = new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
                byte[] lock_val = String.valueOf(expireAt).getBytes(utf8);
                // key不存在写入  key存在忽略
                Boolean acquire = connection.setNX(lock_key, lock_val);
                connection.expire(lock_key, LOCK_TTL);
                if (acquire) {
                    return true;
                } else {
                    byte[] value = connection.get(lock_key);
                    if (Objects.nonNull(value) && value.length > 0) {
                        long expireTime = Long.parseLong(new String(value, utf8));
                        // 已经过期
                        boolean exp = System.currentTimeMillis() > expireTime;
                        if (exp) {
                            // 防止并发修改
                            byte[] ext = connection.getSet(lock_key, lock_val);
                            connection.expire(lock_key, LOCK_TTL);
                            long extExpireTime = Long.parseLong(new String(ext, utf8));
                            return System.currentTimeMillis() > extExpireTime;
                        }
                    }
                }
                return false;
            }
        };

        return redisTemplate.execute(redisCallback);
    }

    /**
     * 解锁
     */
    @Deprecated
    public static void unlock(StringRedisTemplate redisTemplate, String key) {
        String lock = "redis_lock:key_" + key;
        Charset utf8 = StandardCharsets.UTF_8;
        byte[] lock_key = lock.getBytes(utf8);

        RedisCallback<Long> redisCallback = new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(lock_key);
            }
        };
        redisTemplate.execute(redisCallback);
    }

    /**
     * 阻塞锁
     */
    @Deprecated
    public static void lockBlocking(StringRedisTemplate redisTemplate, String key, int expireSec) {
        long curTime = System.currentTimeMillis();
        while (true) {
            boolean lock = lock(redisTemplate, key, expireSec);
            if (lock) {
                return;
            }
            long deltaTime = System.currentTimeMillis() - curTime;
            if (deltaTime > (expireSec + 1) * 1000) {
                return;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
