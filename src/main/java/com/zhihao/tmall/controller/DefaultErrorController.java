/**
 * 
 */
package com.zhihao.tmall.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zzh
 * 2018年9月25日
 */
@Controller
@RequestMapping("")
public class DefaultErrorController implements ErrorController {

	private static final String ERROR_PATH = "/error";

	@GetMapping(value=ERROR_PATH)
	public String notFoundError() {
		return "error/404";
	}
	
	@Override
	public String getErrorPath() {
		String errorPath = ERROR_PATH;
		return errorPath;
	}

}
