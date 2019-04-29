package org.xiaobo.mybatis.moredatasource.aop.conf;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "org.xiaobo.mybatis.moredatasource.aop.repository", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig {
	
	@Value("${spring.datasource.primary.driver-class-name}")
	private String primaryDirverClassName;
	@Value("${spring.datasource.primary.url}")
	private String primaryUrl;
	@Value("${spring.datasource.primary.username}")
	private String primaryUsername;
	@Value("${spring.datasource.primary.password}")
	private String primaryPassword;
	
	@Value("${spring.datasource.secondary.driver-class-name}")
	private String secondaryDirverClassName;
	@Value("${spring.datasource.secondary.url}")
	private String secondaryUrl;
	@Value("${spring.datasource.secondary.username}")
	private String secondaryUsername;
	@Value("${spring.datasource.secondary.password}")
	private String secondaryPassword;
	
	
	@Bean
	public DataSourceProperties DataSourceProperties() { // 2
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		dataSourceProperties.setDriverClassName(primaryDirverClassName);
		dataSourceProperties.setUrl(primaryUrl);
		dataSourceProperties.setUsername(primaryUsername);
		dataSourceProperties.setPassword(primaryPassword);
		return dataSourceProperties;
	}
	
	@Primary
	@Bean
	public DataSourceProperties primaryDataSourceProperties() { // 2
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		dataSourceProperties.setDriverClassName(primaryDirverClassName);
		dataSourceProperties.setUrl(primaryUrl);
		dataSourceProperties.setUsername(primaryUsername);
		dataSourceProperties.setPassword(primaryPassword);
		return dataSourceProperties;
	}
	
	@Bean
	public DataSourceProperties secondaryDataSourceProperties() { // 2
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		dataSourceProperties.setDriverClassName(secondaryDirverClassName);
		dataSourceProperties.setUrl(secondaryUrl);
		dataSourceProperties.setUsername(secondaryUsername);
		dataSourceProperties.setPassword(secondaryPassword);
		return dataSourceProperties;
	}

	@Primary
	@Bean(name = "primaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.primary")
	public javax.sql.DataSource primaryDataSource() {
		DataSourceProperties primaryDataSourceProperties = primaryDataSourceProperties();
		return DataSourceBuilder.create().driverClassName(primaryDataSourceProperties.getDriverClassName())
				.url(primaryDataSourceProperties.getUrl()).username(primaryDataSourceProperties.getUsername())
				.password(primaryDataSourceProperties.getPassword()).build();
	}

	@Bean(name = "secondaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.secondary")
	public javax.sql.DataSource secondaryDataSource() {
		DataSourceProperties secondaryDataSourceProperties = secondaryDataSourceProperties();
		return DataSourceBuilder.create().driverClassName(secondaryDataSourceProperties.getDriverClassName())
				.url(secondaryDataSourceProperties.getUrl()).username(secondaryDataSourceProperties.getUsername())
				.password(secondaryDataSourceProperties.getPassword()).build();
	}

	@Bean(name = "dynamicDataSource")
	public DynamicDataSource dynamicDataSource() {
		Map<Object, Object> targetDataSource = new HashMap<>();
		targetDataSource.put(DataSourceType.DataBaseType.PRIMARY, primaryDataSource());
		targetDataSource.put(DataSourceType.DataBaseType.SECONDARY, secondaryDataSource());
		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSource);
		dataSource.setDefaultTargetDataSource(primaryDataSource());
		return dataSource;
	}

	@Bean(name = "SqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dynamicDataSource());
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
		return bean.getObject();
	}
	
	//配置事务管理器  只配置一个即可
    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager primaryTransactionManager() {
        return new DataSourceTransactionManager(primaryDataSource());
    }

    @Bean(name = "SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate primarySqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }
    
    
//    @Bean(name = "secondarySqlSessionFactory")
//	public SqlSessionFactory secondarySqlSessionFactory(
//			@Qualifier("dynamicDataSource") javax.sql.DataSource dynamicDataSource) throws Exception {
//		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//		bean.setDataSource(dynamicDataSource);
//		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
//		return bean.getObject();
//	}
//	
//	//配置事务管理器
//    @Bean(name = "secondaryTransactionManager")
//    public DataSourceTransactionManager secondaryTransactionManager() {
//        return new DataSourceTransactionManager(secondaryDataSource());
//    }

//    @Bean(name = "secondarySqlSessionTemplate")
//    public SqlSessionTemplate secondarySqlSessionTemplate() throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory());
//    }
    
    
    

}