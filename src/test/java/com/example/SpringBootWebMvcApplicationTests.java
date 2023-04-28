package com.example;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class SpringBootWebMvcApplicationTests {

    @Resource
    JavaMailSender sender;

    @Value("${spring.mail.username}")
    String myMail;

    @Test
    void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("黄星强是大傻逼！！！");
        message.setTo("2943058551@qq.com");
        message.setFrom(myMail);
        sender.send(message);
    }

}
