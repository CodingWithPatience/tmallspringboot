/**
 * 
 */
package com.zhihao.tmall.config.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author zzh
 * 2018年7月27日
 */
@Configuration
@MapperScan("com.zhihao.tmall.mapper")
public class DataSourceConfiguration {

	@Autowired
	private DataSourceProperty dataSourceProperty;
	
	@Bean(name="dataSource")
	public DruidDataSource createDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		
		// 配置数据库连接池
		dataSource.setDriverClassName(dataSourceProperty.getDriver());
		dataSource.setUrl(dataSourceProperty.getUrl());
		dataSource.setUsername(dataSourceProperty.getUsername());
		dataSource.setPassword(dataSourceProperty.getPassword());
		dataSource.setInitialSize(dataSourceProperty.getInitialSize());
		dataSource.setMinIdle(dataSourceProperty.getMinIdle());
		dataSource.setMaxActive(dataSourceProperty.getMaxActive());
		dataSource.setMinEvictableIdleTimeMillis(dataSourceProperty.getMinEvictableIdleTimeMillis());
		dataSource.setTimeBetweenEvictionRunsMillis(dataSourceProperty.getTimeBetweenEvictionRunsMillis());
		dataSource.setPoolPreparedStatements(dataSourceProperty.getPoolPreparedStatements());
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceProperty.getMaxPoolPreparedStatementPerConnectionSize());
		dataSource.setDefaultAutoCommit(dataSourceProperty.getDefaultAutoCommit());
		return dataSource;
	}
}
