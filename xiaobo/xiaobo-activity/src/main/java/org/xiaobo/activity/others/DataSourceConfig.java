package org.xiaobo.activity.others;
//package org.xiaobo.activity.conf;
//
//import javax.sql.DataSource;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Configuration
//public class DataSourceConfig {
//    @Bean("master")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource(){
//        return DataSourceBuilder.create().build();
//    }
//    @Bean("slave")
//    @ConfigurationProperties(prefix = "spring.slave.datasource")
//    public DataSource dataSourceSlave(){
//        return DataSourceBuilder.create().build();
//    }
//}