package org.xiaobo.mybatis.moredatasource.conf;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件上传 坑   路径配置 不配置容易造成不稳定
 * @author xiaobo
 * @date 2019年4月20日
 */

@Configuration
public class FileConfig {

    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大KB,MB
        factory.setMaxFileSize("-1");
        // 设置总上传数据总大小
        factory.setMaxRequestSize("-1");
        // 修改临时文件保存路径
        factory.setLocation("/data/java_app/tmp");
        return factory.createMultipartConfig();
    }
}
