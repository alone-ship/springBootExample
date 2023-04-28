package com.example.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;

public class mybatisRedisCache implements Cache {
    //记录缓存的key
    private int KEYS = 0;

    private String ID;

    public static RedisTemplate<Object, Object> redisTemplate;

    public mybatisRedisCache(String ID) {
        this.ID = ID;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void putObject(Object o, Object o1) {
        redisTemplate.opsForValue().set(o,o1,60, TimeUnit.SECONDS);
        KEYS++;
    }

    @Override
    public Object getObject(Object o) {
        return redisTemplate.opsForValue().get(0);
    }

    @Override
    public Object removeObject(Object o) {
        Boolean delete = redisTemplate.delete(o);
        KEYS--;
        return delete;
    }

    @Override
    public void clear() {
        //清空redisTemplate
        RedisConnection connection = RedisConnectionUtils.getConnection(redisTemplate.getConnectionFactory());
        connection.flushAll();
    }

    @Override
    public int getSize() {
        return KEYS;
    }
}
