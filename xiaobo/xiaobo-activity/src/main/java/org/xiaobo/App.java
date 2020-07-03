//package org.xiaobo;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
///**
// * Hello world!
// *(1) @ComponentScan(basePackages = "xxx.xxx.xxx")：扫描 @Controller、@Service 注解；
//(2) @EnableJpaRepositories(basePackages = "xxx.xxx.xxx")：扫描 @Repository 注解；
//(3) @EntityScan(basePackages ="xxx.xxx.xxx")：扫描 @Entity 注解；
//--------------------- 
//作者：卧龙腾飞 
//来源：CSDN 
//原文：https://blog.csdn.net/jakemanse/article/details/83271442 
//版权声明：本文为博主原创文章，转载请附上博文链接！
// */
//@EntityScan(basePackages = {"org.xiaobo.activity.primary.entity","org.xiaobo.activity.second.entity"})
//@EnableJpaRepositories(basePackages = {"org.xiaobo.activity.primary.dao","org.xiaobo.activity.second.dao"})
//@ComponentScan(basePackages = {"org.xiaobo.activity"})
//@SpringBootApplication
//public class App {
//	public static void main(String[] args) {
//		SpringApplication.run(App.class, args);
//	}
//}
