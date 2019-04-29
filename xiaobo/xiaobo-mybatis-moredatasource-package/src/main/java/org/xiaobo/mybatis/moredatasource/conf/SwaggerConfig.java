package org.xiaobo.mybatis.moredatasource.conf;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 文档生成
 * @author xiaobo
 * @date 2019年4月20日
 */

@Configuration
@EnableSwagger2
//@Profile({"dev","test"})
@ComponentScan(basePackages ={"org.xiaobo.mybatis.moredatasource.**.controller"})
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // Flag to enable or disable possibly loaded using a property file
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.xiaobo.mybatis.moredatasource.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> result = new ArrayList();
        result.add(new ApiKey("authorization", "authorization", "header"));
        return result;
    }
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> result = new ArrayList();
        result.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build());
        return result;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> result = new ArrayList();
        result.add(new SecurityReference("authorization", authorizationScopes));
        return result;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("接口")
                .version("1.0")
                .build();
    }
}