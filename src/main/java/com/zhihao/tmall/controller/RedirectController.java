package com.zhihao.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于处理重定向的请求转发以及只返回页面的简单请求的控制器
 * @author zzh
 * 2018年8月26日
 */
@Controller
@RequestMapping("")
public class RedirectController {
	
	// 返回注册页
    @RequestMapping("register")
    public String registerPage() {
        return "fore/register";
    }
    
    // 返回注册成功的页面
    @RequestMapping("registerSuccess")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }
    
    // 返回登录页
    @RequestMapping("login")
    public String loginPage() {
        return "fore/login";
    }
    
    // 返回搜索结果页
    @GetMapping("searchResult")
    public String searchResult() {
    	return "fore/searchResult";
    }
}

