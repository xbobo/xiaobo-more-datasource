package org.xiaobo.mybatis.moredatasource.conf;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "org.xiaobo.mybatis.moredatasource.repository.secondary", sqlSessionFactoryRef = "secondarySqlSessionFactory")
public class DataSourceConfigSecondary {
	
	@Value("${spring.datasource.secondary.driver-class-name}")
	private String secondaryDirverClassName;
	@Value("${spring.datasource.secondary.url}")
	private String secondaryUrl;
	@Value("${spring.datasource.secondary.username}")
	private String secondaryUsername;
	@Value("${spring.datasource.secondary.password}")
	private String secondaryPassword;
	
	
	@Bean
	public DataSourceProperties secondaryDataSourceProperties() { // 2
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		dataSourceProperties.setDriverClassName(secondaryDirverClassName);
		dataSourceProperties.setUrl(secondaryUrl);
		dataSourceProperties.setUsername(secondaryUsername);
		dataSourceProperties.setPassword(secondaryPassword);
		return dataSourceProperties;
	}
	
	@Bean(name = "secondaryDataSource")
	public DataSource getDateSource() {
		DataSourceProperties secondaryDataSourceProperties = secondaryDataSourceProperties();
		return DataSourceBuilder.create().driverClassName(secondaryDataSourceProperties.getDriverClassName())
				.url(secondaryDataSourceProperties.getUrl()).username(secondaryDataSourceProperties.getUsername())
				.password(secondaryDataSourceProperties.getPassword()).build();
	}

	@Bean(name = "secondarySqlSessionFactory")
	public SqlSessionFactory secondarySqlSessionFactory()
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(getDateSource());
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/secondary/*.xml"));
		return bean.getObject();
	}

	@Bean("secondarySqlSessionTemplate")
	public SqlSessionTemplate secondarysqlsessiontemplate() throws Exception {
		return new SqlSessionTemplate(secondarySqlSessionFactory());
	}
	
}