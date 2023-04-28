package com.example.service.impl;

import com.example.entity.account;
import com.example.mapper.accountMapper;
import com.example.service.accountService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class accountImpl implements accountService, UserDetailsService {

    @Resource
    accountMapper accountMapper;

    @Override
    public account accountDetails(String username) {
        return accountMapper.accountByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //accountMapper获取账号
        account account = accountMapper.accountByUsername(username);
        //判断account
        if (account == null)
            throw new UsernameNotFoundException("账号不存在");
        return User
                .withUsername(account.getAccount())
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }
}
