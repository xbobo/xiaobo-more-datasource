package org.xiaobo.mybatis.moredatasource.aop.repository;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.xiaobo.mybatis.moredatasource.aop.entity.Message;

public interface MessageRepository {

	@InsertProvider(type = MessageProvider.class,method = "save")
	@Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
	int save(Message message);

	@UpdateProvider(type = MessageProvider.class,method = "update")
	int update(Message message);
	
	@DeleteProvider(type = MessageProvider.class,method = "remove")
	int remove(Message message);
	
	@SelectProvider(type = MessageProvider.class,method = "find")
	@Results(id = "messageFindResultList",value = {
			@Result(column ="id" ,property = "id"),
			@Result(column ="name" ,property = "name"),
			@Result(column ="content" ,property = "content")
	})
	List<Message> findAll(Message message);
 

}
