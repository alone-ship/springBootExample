package com.example.security;

import com.example.cache.redisTokenRepository;
import com.example.entity.account;
import com.example.service.impl.accountImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
public class securityConfiguration {

    @Resource
    redisTokenRepository redisTokenRepository;

    @Resource
    accountImpl accountImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/css/**","/js/**")
                .permitAll()
                .anyRequest().hasAnyRole("user","admin")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .successHandler(this::onAuthenticationSuccess)
                .permitAll()
                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(redisTokenRepository)
                .and()
                .csrf()
                .disable();
        return http.build();
    }

    @Resource
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(accountImpl)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        HttpSession session = request.getSession();
        account account = accountImpl.accountDetails(authentication.getName());
        session.setAttribute("account", account);
        response.sendRedirect("/index");
    }
}
