package org.xiaobo.mybatis.moredatasource.aop.conf;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.xiaobo.mybatis.moredatasource.aop.conf.DataSourceType.DataBaseType;

@Aspect
@Component
public class DataSourceAop {
	// org.xiaobo.mybatis.moredatasource.aop.service..*.primary*(..))
    @Before("execution(* org.xiaobo.mybatis.moredatasource.aop.service.primary..*(..))")
    public void setDataSourcePrimary() {
        System.err.println("primary业务");
        DataSourceType.setDataBaseType(DataBaseType.PRIMARY);
    }

    @Before("execution(* org.xiaobo.mybatis.moredatasource.aop.service.secondary..*(..))")
    public void setDataSourceSecondary() {
        System.err.println("secondary业务");
        DataSourceType.setDataBaseType(DataBaseType.SECONDARY);
    }
}
