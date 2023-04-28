package com.example.controller;

import com.example.entity.account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
public class authViewController {

    @GetMapping("/index")
    private String index(@SessionAttribute @RequestParam(required = false) account account, Model model){
        if(account != null){
            log.info("用户："+account.getAccount()+"进入了系统");
            model.addAttribute("account", account);
        }
        return "index";
    }
}
