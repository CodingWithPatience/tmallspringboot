package com.zhihao.tmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.zhihao.tmall.mapper")
public class TmallSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmallSpringbootApplication.class, args);
	}
}
