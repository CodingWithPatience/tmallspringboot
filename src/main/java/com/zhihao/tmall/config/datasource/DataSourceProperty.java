/**
 * 
 */
package com.zhihao.tmall.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据库连接池配置类
 * 从application.yml配置文件加载datasource前缀的配置
 * 类属性名与配置属性名一一对应
 * @author zzh
 * 2018年8月4日
 */
@Component
@ConfigurationProperties(prefix="datasource")
public class DataSourceProperty {
	private String username;
	private String password;
	private String url;
	private String driver;
	private int initialSize;
	private int minIdle;
	private int maxActive;
	private long maxWait;
	private long minEvictableIdleTimeMillis;
	private long timeBetweenEvictionRunsMillis;
	private boolean poolPreparedStatements;
	private boolean defaultAutoCommit;
	private int maxPoolPreparedStatementPerConnectionSize;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public long getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}
	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}
	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	public boolean getPoolPreparedStatements() {
		return poolPreparedStatements;
	}
	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}
	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}
	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}
	public boolean getDefaultAutoCommit() {
		return defaultAutoCommit;
	}
	public void setDefaultAutoCommit(boolean defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}
}
