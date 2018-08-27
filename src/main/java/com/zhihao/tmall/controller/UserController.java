package com.zhihao.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping(value= {"list", "list/{pageNum}"})
    public String list(Model model, 
    		@PathVariable(required=false) String pageNum, Page page){
    	page.setCurrentPage(pageNum);
    	page.setParam("user");
        List<User> us = userService.list(page);
        long total = userService.getTotal();
        page.setTotal(total);

        model.addAttribute("us", us);
        model.addAttribute("page", page);
        return "admin/listUser";
    }
}
