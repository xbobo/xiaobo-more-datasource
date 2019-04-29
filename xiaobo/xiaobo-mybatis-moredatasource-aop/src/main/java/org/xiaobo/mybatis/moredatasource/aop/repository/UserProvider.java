package org.xiaobo.mybatis.moredatasource.aop.repository;

import org.apache.ibatis.jdbc.SQL;
import org.xiaobo.mybatis.moredatasource.aop.entity.User;

public class UserProvider {
	
	public static final String TABLE=" `user` ";

	public String save(User user) {
		SQL sql=new SQL();
		sql.INSERT_INTO(TABLE);
		sql.VALUES("name", "#{name}");
		sql.VALUES("age", "#{age}");
		return sql.toString();
	}
	
	public String update(User user) {
		SQL sql=new SQL();
		sql.UPDATE(TABLE);
		sql.SET(" name =#{name}");
		sql.SET("age = #{age}");
		if(user.getId()!=null) {
			sql.WHERE(" id = #{id}");
		}
		return sql.toString();
	}
	
	public String findAll(User user) {
		SQL sql=new SQL();
		sql.SELECT("id,age,name");
		sql.FROM(TABLE);
		if(user.getId()!=null) {
			sql.WHERE(" id = #{id}");
		}
		return sql.toString();
	}
	
	public String remove(User user) {
		SQL sql=new SQL();
		sql.DELETE_FROM(TABLE);
		if(user.getId()!=null) {
			sql.WHERE(" id = #{id}");
		}
		return sql.toString();
	}
}
