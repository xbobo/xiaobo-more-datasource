package org.xiaobo.mybatis.moredatasource.repository.secondary;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.xiaobo.mybatis.moredatasource.entity.Message;

public class MessageProvider {

	public static final String TABLE=" `message` ";

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
	
	public String removeBatch(@Param(value = "ids") List ids) {
		SQL sql=new SQL();
		sql.DELETE_FROM(TABLE);
		String idsStr="";
		if(ids!=null&& ids.size()>0) {
			for(Object id:ids) {
				idsStr+=id+",";
			}
			idsStr=idsStr.substring(0, idsStr.length()-1);
		}
		sql.WHERE(" id in ("+idsStr+")");
		return sql.toString();
	}
	
	
	public static void main(String[] args) {
		List<Message> ms=new ArrayList<Message>();
		Message e=new Message();
		e.setName("test1");
		e.setContent("content1");
		ms.add(e);
		Message e1=new Message();
		e1.setName("test1");
		e1.setContent("content1");
		ms.add(e1);
		
		StringBuffer saveBuffer=new StringBuffer();
		for(Message single:ms) {
			SQL sql=new SQL();
			sql.INSERT_INTO(TABLE);
			sql.VALUES("`name`","#{single.name}");
			sql.VALUES("`content`", "#{single.content}");
			saveBuffer.append(sql.toString()+";");
		}
		System.out.println(saveBuffer.toString());
	}
}
