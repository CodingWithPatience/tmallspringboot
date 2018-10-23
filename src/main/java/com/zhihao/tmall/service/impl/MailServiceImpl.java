/**
 * 
 */
package com.zhihao.tmall.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.zhihao.tmall.config.mail.MailProperty;
import com.zhihao.tmall.pojo.User;
import com.zhihao.tmall.service.MailService;
import com.zhihao.tmall.service.UserService;
import com.zhihao.tmall.util.MailThread;
import com.zhihao.tmall.util.MailUUIDUtil;

/**
 * 用户注册邮件认证
 * @author zzh
 * 2018年9月20日
 */
@Service
public class MailServiceImpl implements MailService {
	
	@Value("${mail.username}")
	private String username;
	@Value("${mail.code}")
	private String code;
	@Value("${mail.subject}")
	private String subject;
	

	@Autowired
	UserService userService;
	
	@Autowired
	TemplateEngine engine;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	MailProperty mailProperty;
	
	/** 
	 * 用户注册，邮件认证
	 * @param code 发送到注册用户邮件中的激活码
	 */
	@Override
	public boolean mailAuth(String code) { 
		User user = userService.getByCode(code);
		if(user != null) {
			user.setStatus(UserService.ACTIVE);      // 将用户设置为已激活状态
			user.setCode(null);                      // 将激活码设置为null
			userService.update(user);                // 在数据库中更新用户，用户注册成功
			return true;
		}
		else
			return false;
	}

	/**
	 * UUID(Universally Unique Identifier)
	 * 生成32位的随机字符串，作为邮箱认证激活码
	 */
	@Override
	public String generateCode() {
		String code = MailUUIDUtil.mailUUID().replace("-", "");
		return code;
	}

	// 账户激活邮件发送
	@Override
	public void sendMail(String contextPath, User user) {
		Context context = new Context();
		context.setVariable("contextPath", contextPath);
		context.setVariable(username, user.getName());
		context.setVariable(code, user.getCode());
		String content = engine.process("mail/mailTemplate", context);
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(mailProperty.getUsername());
			helper.setTo(user.getMailAccount());
			helper.setSubject(subject);
			helper.setText(content, true);
			
			// 创建一个发送邮件的线程
			MailThread mailThread = new MailThread(mailSender, message);
			mailThread.start();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
