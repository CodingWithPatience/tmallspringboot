/**
 * 
 */
package com.zhihao.tmall.config.datasource;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.github.pagehelper.PageInterceptor;

/**
 * @author zzh
 * 2018年7月27日
 */
@Configuration
@MapperScan("com.zhihao.tmall.mapper")
public class SessionFactoryConfiguration {

	@Value("${mybatis-config-file}")
	private String mybatisConfigFilePath;
	@Value("${mapper-path}")
	private String mapperPath;
	@Value("${pojo-package}")
	private String pojoPackage;

	@Autowired
	private DataSource dataSource;
	
	/**
	 * 创建sqlSessionFactoryBean
	 * 配置mybatis-config、mapper.xml文件路径
	 * 设置datasource及扫描的pojo包
	 * @throws Exception
	 * @return SqlSessionFactory
	 * 2018年7月27日
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory createSqlSessionFactoryBean(Interceptor[] interceptor) throws Exception { 
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage(pojoPackage);
//		sqlSessionFactoryBean.setPlugins(interceptor);
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name="interceptor")
	public Interceptor[] ceratePageInterceptor() {
		Interceptor[] interceptors = new PageInterceptor[1];
		Properties properties = new Properties();
		properties.setProperty("dialect", "com.github.pagehelper.dialect.helper.MySqlDialect");
		properties.setProperty("reasonable", "false");
		properties.setProperty("pageSizeZero", "true");
		PageInterceptor pageInterceptor = new PageInterceptor();
		pageInterceptor.setProperties(properties);
		interceptors[0] = pageInterceptor;
		return interceptors;
	}
}
