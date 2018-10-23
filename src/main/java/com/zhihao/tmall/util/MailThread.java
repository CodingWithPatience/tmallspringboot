/**
 * 
 */
package com.zhihao.tmall.util;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;

/**
 * 处理邮件发送的线程类
 * @author zzh
 * 2018年9月21日
 */
public class MailThread extends Thread {
	private JavaMailSender mailSender;
	private MimeMessage message;

	public MailThread(JavaMailSender mailSender, MimeMessage message) {
		this.mailSender = mailSender;
		this.message = message;
	}
	
	@Override
	public void run() {
		mailSender.send(message);
	}
}
