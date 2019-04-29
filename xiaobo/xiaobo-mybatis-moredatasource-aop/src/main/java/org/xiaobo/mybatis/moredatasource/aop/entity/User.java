package org.xiaobo.mybatis.moredatasource.aop.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable{

    private Long id;

    private String name;

    private Integer age;

    public User(){}

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}
