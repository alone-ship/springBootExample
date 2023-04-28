package com.example.mapper;

import com.example.cache.mybatisRedisCache;
import com.example.entity.account;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@CacheNamespace(implementation = mybatisRedisCache.class)
@Mapper
public interface accountMapper {
    @Select("select * from account where account = #{username} or email = #{username}")
    account accountByUsername(String username);
}
