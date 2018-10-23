package com.zhihao.tmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
public class TmallSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmallSpringbootApplication.class, args);
	}
}
