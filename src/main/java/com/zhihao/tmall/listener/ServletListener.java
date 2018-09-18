/**
 * 
 */
package com.zhihao.tmall.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author zzh
 * 2018年9月9日
 */
@WebListener("servletListener")
public class ServletListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("servlet初始化");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("servlet销毁");
	}

}
