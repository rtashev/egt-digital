package com.egt.gateway.util;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Implementation taken from - https://github.com/himalayaRange/springBoot/blob/master/src/main/java/org/wy/spring/boot/redis/RedisUtil.java
 */
@Component
@AllArgsConstructor
public class RedisUtil {

    private final RedisTemplate redisTemplate;

    public void remove(String... keys) {
        String[] var2 = keys;
        int var3 = keys.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            this.remove(key);
        }

    }

    public void removePattern(String pattern) {
        Set keys = this.redisTemplate.keys(pattern);
        if(keys.size() > 0) {
            this.redisTemplate.delete(keys);
        }

    }

    public void remove(String key) {
        if(this.exists(key)) {
            this.redisTemplate.delete(key);
        }

    }

    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key).booleanValue();
    }

    public Object get(String key) {
        Object result = null;
        ValueOperations operations = this.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    public boolean set(String key, Object value) {
        boolean result = false;

        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            e.set(key, value);
            result = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;

        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            e.set(key, value);
            this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
            result = true;
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }

        return result;
    }

    public boolean setIfAbsent(String key, Object value) {
        boolean result = false;

        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            result = e.setIfAbsent(key, value).booleanValue();
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }

        return result;
    }

    public boolean setIfAbsent(String key, Object value, Long expireTime) {
        boolean result = false;

        try {
            ValueOperations e = this.redisTemplate.opsForValue();
            result = e.setIfAbsent(key, value).booleanValue();
            if(result) {
                this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
            }
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }

        return result;
    }

    public boolean tryLock(String key, Long cacheSeconds) {
        boolean isLock = false;

        try {
            isLock = this.setIfAbsent(key, "", cacheSeconds);
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }

        return isLock;
    }
}