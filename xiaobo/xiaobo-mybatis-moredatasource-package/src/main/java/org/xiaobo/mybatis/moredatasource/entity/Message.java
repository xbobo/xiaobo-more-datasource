package org.xiaobo.mybatis.moredatasource.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Message implements Serializable{

    private Long id;

    private String name;

    private String content;

    public Message(){}

    public Message(String name, String content) {
        this.name = name;
        this.content = content;
    }

    // 省略getter、setter

}
