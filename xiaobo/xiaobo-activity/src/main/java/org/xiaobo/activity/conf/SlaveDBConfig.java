package org.xiaobo.activity.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = "${spring.datasource.slave.repository.scanpath}",
        entityManagerFactoryRef = "slaveEntityManagerFactory",
        transactionManagerRef = "slaveTransactionManager"
)
public class SlaveDBConfig {
	
	@Value("${spring.datasource.slave.driver-class-name}")
	private String dirverClassName;
	@Value("${spring.datasource.slave.url}")
	private String url;
	@Value("${spring.datasource.slave.username}")
	private String username;
	@Value("${spring.datasource.slave.password}")
	private String password;
	@Value("${spring.datasource.slave.initialize}")
	private Boolean initialize;
	@Value("${spring.datasource.slave.script}")
	private String script;
	@Value("${spring.datasource.slave.scanpath}")
	private String scanpath;


    @Autowired
    private Environment env;

    @Bean
    public DataSourceProperties slaveDataSourceProperties() {
    	DataSourceProperties dataSourceProperties = new DataSourceProperties();
    	dataSourceProperties.setDriverClassName(dirverClassName);
    	dataSourceProperties.setUrl(url);
    	dataSourceProperties.setUsername(username);
    	dataSourceProperties.setPassword(password);
        return dataSourceProperties;
    }

    @Bean
    public DataSource slaveDataSource() {
        DataSourceProperties primaryDataSourceProperties = slaveDataSourceProperties();
        return DataSourceBuilder.create()
                .driverClassName(primaryDataSourceProperties.getDriverClassName())
                .url(primaryDataSourceProperties.getUrl())
                .username(primaryDataSourceProperties.getUsername())
                .password(primaryDataSourceProperties.getPassword())
                .build();
    }

    @Bean
    public PlatformTransactionManager slaveTransactionManager() {
        EntityManagerFactory factory = slaveEntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean slaveEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new
                LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(slaveDataSource());
        factory.setPackagesToScan(scanpath);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto",env.getProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("hibernate.show-sql"));
        jpaProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        factory.setJpaProperties(jpaProperties);
        return factory;
    }

    @Bean
    public DataSourceInitializer slaveDataSourceInitializer() {
        DataSourceInitializer dsInitializer = new DataSourceInitializer();
        dsInitializer.setDataSource(slaveDataSource());
        ResourceDatabasePopulator dbPopulator = new ResourceDatabasePopulator();
       // dbPopulator.addScript(new ClassPathResource(script));
        dsInitializer.setDatabasePopulator(dbPopulator);
//        dsInitializer.setEnabled(env.getProperty("datasource.slave.initialize",
//                Boolean.class, false));
        dsInitializer.setEnabled(initialize);
        return dsInitializer;
    }
}