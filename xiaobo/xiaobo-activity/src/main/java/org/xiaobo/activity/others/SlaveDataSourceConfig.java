package org.xiaobo.activity.others;
//package org.xiaobo.activity.conf;
//
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef="entityManagerFactorySlave",
//        transactionManagerRef = "transactionManagerSlave",
//        basePackages = {"org.xiaobo.activity.second"}
//)
//public class SlaveDataSourceConfig {
//    @Autowired
//    @Qualifier("slave")
//    private DataSource dataSource;
//    @Autowired
//    private JpaProperties jpaProperties;
//    @Bean("entityManagerFactorySlave")
//    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
//        return builder.dataSource(dataSource)
//                .properties(getVendorProperties())
//                .packages("org.xiaobo.activity.second")
//                .build();
//    }
//
//
//    @Bean("entityManagerSlave")
//    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//        return localContainerEntityManagerFactoryBean(builder).getObject().createEntityManager();
//    }
//
//    @Bean("transactionManagerSlave")
//    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
//        return new JpaTransactionManager(localContainerEntityManagerFactoryBean(builder).getObject());
//    }
//
//    private Map getVendorProperties() {
//        HibernateSettings hibernateSettings = new HibernateSettings();
////        hibernateSettings.ddlAuto(ddlAuto);
//        return jpaProperties.getProperties();
//    }
//}