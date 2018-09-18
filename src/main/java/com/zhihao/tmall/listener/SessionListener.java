/**
 * 
 */
package com.zhihao.tmall.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author zzh
 * 2018年9月9日
 */
@WebListener("sessionListener")
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {

		System.out.println("sessionId："+se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

		System.out.println("session销毁时间"+se.getSession().getCreationTime());
	}

}
