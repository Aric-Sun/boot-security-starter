package com.zimug.courses.security.basic.controller;

import com.zimug.courses.security.basic.model.PersonDemo;
import com.zimug.courses.security.basic.service.MethodELService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BizpageController {

    @Resource
    private MethodELService methodELService;

    // 登录
//    @PostMapping("/login")
//    public String index(String username,String password) {
//        return "index";
//    }

    // 日志管理
    @GetMapping("/syslog")
    public String showOrder() {
        return "syslog";
    }

    // 用户管理
    @GetMapping("/sysuser")
    public String addOrder() {
        return "sysuser";
    }

    // 具体业务一
    @GetMapping("/biz1")
    public String updateOrder() {

        methodELService.findAll();
        methodELService.findOne();
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        methodELService.delete(ids,null);
        List<PersonDemo> pds = methodELService.findAllPD();

        return "biz1";
    }

    // 具体业务二
    @GetMapping("/biz2")
    public String deleteOrder() {
        return "biz2";
    }


}