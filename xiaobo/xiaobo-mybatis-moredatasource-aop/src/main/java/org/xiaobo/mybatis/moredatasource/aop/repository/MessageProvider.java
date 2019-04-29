package org.xiaobo.mybatis.moredatasource.aop.repository;

import org.apache.ibatis.jdbc.SQL;
import org.xiaobo.mybatis.moredatasource.aop.entity.Message;

public class MessageProvider {

	public static final String TABLE=" `message` ";
	
	public static final String CLOUMNS=" ``";

	public String save(Message message) {
		SQL sql=new SQL();
		sql.INSERT_INTO(TABLE);
		sql.VALUES("name", "#{name}");
		sql.VALUES("content", "#{content}");
		return sql.toString();
	}
	
	public String update(Message user) {
		SQL sql=new SQL();
		sql.UPDATE(TABLE);
		sql.SET(" name =#{name}");
		sql.SET("content = #{content}");
		if(user.getId()!=null) {
			sql.WHERE(" id = #{id}");
		}
		return sql.toString();
	}
	
	public String find(Message user) {
		SQL sql=new SQL();
		sql.SELECT("id,content,name");
		sql.FROM(TABLE);
		if(user.getId()!=null) {
			sql.WHERE(" id = #{id}");
		}
		return sql.toString();
	}
	
	public String remove(Message user) {
		SQL sql=new SQL();
		sql.DELETE_FROM(TABLE);
		if(user.getId()!=null) {
			sql.WHERE(" id = #{id}");
		}
		return sql.toString();
	}
}
