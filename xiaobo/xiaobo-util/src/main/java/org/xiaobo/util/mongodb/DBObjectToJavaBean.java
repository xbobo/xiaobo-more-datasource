package org.xiaobo.util.mongodb;

/**
 * @author xiaobo
 * @date 2019/11/25
 * @description:
 */
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.*;

import com.mongodb.*;
import org.apache.commons.beanutils.BeanUtils;

public class DBObjectToJavaBean {

    public static void main(String[] args) throws  Exception {
        //创建一个MongoDB的数据库连接对象，无参数的话它默认连接到当前机器的localhost地址，端口是27017。
        Mongo mongo = new Mongo("192.168.0.215",27917);
//得到一个test的数据库，如果mongoDB中没有这个数据库，当向此库中添加数据的时候会自动创建
        DB db = mongo.getDB("log");

//获取到一个叫做"user"的集合，相当于关系型数据库中的"表"
        DBCollection burying_log = db.getCollection("burying_log");
        DBObject one = burying_log.findOne();
        Map<Object,Object> map = one.toMap();
        for(Map.Entry entry:map.entrySet()){
            System.out.println(entry.getKey()+"::::"+entry.getValue());
        }

    }
}
