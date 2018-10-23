/**
 * 
 */
package com.zhihao.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhihao.tmall.service.MailService;

/**
 * 用户注册，邮箱激活处理器
 * @author zzh
 * 2018年9月20日
 */
@Controller
@RequestMapping("mailauth")
public class MailController {

	@Autowired
	MailService mailService;
	
	// 新注册用户在邮箱中点击激活按钮，根据传过来的code为用户激活账号
	@GetMapping("active")
	public String mailActive(String code) {
		if(mailService.mailAuth(code))
			return "redirect:/registerSuccess";
		else
			return "mail/mailAuthFail";
	}
}
