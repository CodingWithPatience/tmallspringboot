package com.zhihao.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhihao.tmall.constant.PageParam;
import com.zhihao.tmall.pojo.User;
import com.zhihao.tmall.service.UserService;
import com.zhihao.tmall.util.Page;

/**
 * 后台管理用户的控制器，只简单地显示用户
 * @author zzh
 * 2018年8月26日
 */
@Controller
@RequestMapping("admin/user")
public class UserController {
    @Autowired
    UserService userService;
 
    // 分页方式显示用户
    @GetMapping(value= {""})
    public String list(Model model, 
    		@RequestParam(required=false) Integer page, Page userPage){
    	userPage.setCurrentPage(page);
    	userPage.setParam(PageParam.USER);
        List<User> us = userService.list(userPage);
        long total = userService.getTotal();
        userPage.setTotal(total);

        model.addAttribute("us", us);
        model.addAttribute("page", userPage);
        return "admin/listUser";
    }
}
