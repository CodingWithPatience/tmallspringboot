/**
 * 
 */
package com.zhihao.tmall.handler;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zzh
 * 2018年7月27日
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value=Exception.class)
	public ModelAndView exceptionHandler(HttpServletRequest request, Exception e){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("fore/error");
		return mav;
	}
}
