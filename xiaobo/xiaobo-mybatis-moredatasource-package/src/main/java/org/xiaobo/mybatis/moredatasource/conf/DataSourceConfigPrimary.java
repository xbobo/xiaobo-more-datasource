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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

//表示这个类为一个配置类
@Configuration
//配置mybatis的接口类放的地方
@MapperScan(basePackages = "org.xiaobo.mybatis.moredatasource.repository.primary", sqlSessionFactoryRef = "primarySqlSessionFactory")
public class DataSourceConfigPrimary {
	
	@Value("${spring.datasource.primary.driver-class-name}")
	private String primaryDirverClassName;
	@Value("${spring.datasource.primary.url}")
	private String primaryUrl;
	@Value("${spring.datasource.primary.username}")
	private String primaryUsername;
	@Value("${spring.datasource.primary.password}")
	private String primaryPassword;
	
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
	
	// 将这个对象放入Spring容器中
	@Bean(name = "primaryDataSource")
	// 表示这个数据源是默认数据源
	@Primary
	// 读取application.properties中的配置参数映射成为一个对象
	// prefix表示参数的前缀
	//@ConfigurationProperties(prefix = "spring.datasource.primary")
	public DataSource getDateSource() {
		DataSourceProperties primaryDataSourceProperties = primaryDataSourceProperties();
		return DataSourceBuilder.create().driverClassName(primaryDataSourceProperties.getDriverClassName())
				.url(primaryDataSourceProperties.getUrl()).username(primaryDataSourceProperties.getUsername())
				.password(primaryDataSourceProperties.getPassword()).build();
	}

	@Bean(name = "primarySqlSessionFactory")
	// 表示这个数据源是默认数据源
	@Primary
	// @Qualifier表示查找Spring容器中名字为primaryDataSource的对象
	public SqlSessionFactory primarySqlSessionFactory() throws Exception{
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(getDateSource());
		bean.setMapperLocations(
				// 设置mybatis的xml所在位置
				new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/primary/*.xml"));
		return bean.getObject();
	}

	@Bean("primarySqlSessionTemplate")
	// 表示这个数据源是默认数据源
	@Primary
	public SqlSessionTemplate primarysqlsessiontemplate() throws Exception {
		return new SqlSessionTemplate(primarySqlSessionFactory());
	}
	
	//配置事务管理器  只配置一个即可
    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager primaryTransactionManager() {
        return new DataSourceTransactionManager(getDateSource());
    }
}
