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
@EnableJpaRepositories( //0
        basePackages = "${spring.datasource.master.repository.scanpath}",
        entityManagerFactoryRef = "masterEntityManagerFactory",
        transactionManagerRef = "masterTransactionManager"
)
public class MasterDBConfig {
	@Value("${spring.datasource.master.driver-class-name}")
	private String dirverClassName;
	@Value("${spring.datasource.master.url}")
	private String url;
	@Value("${spring.datasource.master.username}")
	private String username;
	@Value("${spring.datasource.master.password}")
	private String password;
	@Value("${spring.datasource.master.initialize}")
	private Boolean initialize;
	@Value("${spring.datasource.master.script}")
	private String script;
	@Value("${spring.datasource.master.scanpath}")
	private String scanpath;

	

    @Autowired
    private Environment env; //1

    @Bean
    public DataSourceProperties masterDataSourceProperties() { //2
    	DataSourceProperties dataSourceProperties = new DataSourceProperties();
    	dataSourceProperties.setDriverClassName(dirverClassName);
    	dataSourceProperties.setUrl(url);
    	dataSourceProperties.setUsername(username);
    	dataSourceProperties.setPassword(password);
        return dataSourceProperties;
    }


    @Bean
    public DataSource masterDataSource() { //3
        DataSourceProperties manDataSourceProperties = masterDataSourceProperties();
        return DataSourceBuilder.create()
                .driverClassName(manDataSourceProperties.getDriverClassName())
                .url(manDataSourceProperties.getUrl())
                .username(manDataSourceProperties.getUsername())
                .password(manDataSourceProperties.getPassword())
                .build();
    }

    @Bean
    public PlatformTransactionManager masterTransactionManager() { //4
        EntityManagerFactory factory = masterEntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {//5
        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(masterDataSource());
        factory.setPackagesToScan(scanpath);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("hibernate.show-sql"));
        jpaProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        factory.setJpaProperties(jpaProperties);
        return factory;
    }

    @Bean
    public DataSourceInitializer masterDataSourceInitializer() {//6
        DataSourceInitializer dsInitializer = new DataSourceInitializer();
        dsInitializer.setDataSource(masterDataSource());
        ResourceDatabasePopulator dbPopulator = new ResourceDatabasePopulator();
       // dbPopulator.addScript(new ClassPathResource(script));
        dsInitializer.setDatabasePopulator(dbPopulator);
//        dsInitializer.setEnabled(env.getProperty("datasource.man.initialize",
//                Boolean.class, false) );
      dsInitializer.setEnabled(initialize);
        return dsInitializer;
    }

}
