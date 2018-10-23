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
	
	// 重定向到登录页
	@GetMapping("")
    public String redirectToLogin() {
    	return "redirect:/login";
    }
	
	// 返回注册页
    @GetMapping("register")
    public String registerPage() {
        return "fore/register";
    }
    
    // 提示到邮箱去激活的页面
    @GetMapping("register/mailauth")
	public String mailAuth() {
		return "fore/mailRegister";
	}
    
    // 返回注册成功的页面
    @GetMapping("registerSuccess")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }
    
    // 返回搜索结果页
    @GetMapping("searchResult")
    public String searchResult() {
    	return "fore/searchResult";
    }
}

