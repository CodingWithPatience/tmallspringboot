/**
 * 
 */
package com.zhihao.tmall.util;

import java.util.UUID;

/**
 * @author zzh
 * 2018年9月20日
 */
public class MailUUIDUtil {

	public static String mailUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
}
