package org.xiaobo.mybatis.moredatasource;

import com.alibaba.fastjson.JSONObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;
import sun.misc.JavaNetAccess;
import sun.misc.Launcher;
import sun.misc.SharedSecrets;
import sun.misc.URLClassPath;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.util.Enumeration;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@MapperScan("org.xiaobo.mybatis.moredatasource.repository")
@EnableTransactionManagement(proxyTargetClass = true) //// 开启事物管理功能
public class App {
	public static void main(String[] args)throws  Exception {
//		   ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

//        Enumeration<URL> resources1 = classLoader.getResources("META-INF/spring.factories");
//        while (resources1.hasMoreElements()){
//            URL url = resources1.nextElement();
//            System.out.println(url.getPath());
//        }

//        System.out.println("11111111111111111111111");
//        Enumeration<URL> resources2 = ClassLoader.getSystemResources("META-INF/spring.factories");
//        while (resources2.hasMoreElements()){
//            URL url = resources2.nextElement();
//            System.out.println(url.getPath());
//        }

          SpringApplication.run(App.class, args);

    }
}
