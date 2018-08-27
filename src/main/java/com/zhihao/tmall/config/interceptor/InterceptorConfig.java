/**
 * 
 */
package com.zhihao.tmall.config.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zhihao.tmall.interceptor.LoginInterceptor;
import com.zhihao.tmall.interceptor.OtherInterceptor;

/**
 * @author zzh
 * 2018年7月30日
 */
@Configuration
@MapperScan("com.zhihao.tmall.interceptor")
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Autowired
	LoginInterceptor loginInterceptor;
	@Autowired
	OtherInterceptor otherInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> patterns = new ArrayList<>();
		patterns.add("/css/**");
		patterns.add("/img/**");
		patterns.add("/js/**");
		registry.addInterceptor(loginInterceptor).excludePathPatterns(patterns);
		registry.addInterceptor(otherInterceptor);
	}
}
