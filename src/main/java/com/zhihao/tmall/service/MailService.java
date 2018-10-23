/**
 * 
 */
package com.zhihao.tmall.service;

import com.zhihao.tmall.pojo.User;

/**
 * @author zzh
 * 2018年9月20日
 */
public interface MailService {

	boolean mailAuth(String code);
	String generateCode();
	void sendMail(String contextPath, User user);
}
