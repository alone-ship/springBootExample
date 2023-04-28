package com.example.config;

import com.example.cache.mybatisRedisCache;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class mybatisCacheConfiguration {
    @Resource
    RedisTemplate<Object, Object> template;

    @PostConstruct
    public void init() {
        mybatisRedisCache.redisTemplate = template;
    }
}
