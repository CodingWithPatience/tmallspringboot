/**
 * 
 */
package com.zhihao.tmall.config.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author zzh
 * 2018年9月20日
 */
@Configuration
public class MailConfiguration {
	
	@Autowired
	MailProperty mailProperty;

	@Bean
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailProperty.getHost());
		mailSender.setProtocol(mailProperty.getProtocol());
		mailSender.setPort(mailProperty.getPort());
		mailSender.setDefaultEncoding(mailProperty.getEncoding());
		mailSender.setUsername(mailProperty.getUsername());
		mailSender.setPassword(mailProperty.getPassword());
		
		return mailSender;
	}
}
