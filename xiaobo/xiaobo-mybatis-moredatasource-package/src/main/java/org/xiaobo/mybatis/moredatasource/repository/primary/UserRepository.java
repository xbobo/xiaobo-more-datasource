package org.xiaobo.mybatis.moredatasource.repository.primary;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.xiaobo.mybatis.moredatasource.entity.User;

public interface UserRepository {

	@InsertProvider(type = UserProvider.class,method = "save")
	@Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
	int save(User user);
	
	@UpdateProvider(type = UserProvider.class,method = "update")
	int update(User user);
	
	@DeleteProvider(type = UserProvider.class,method = "remove")
	int remove(User user);
	
	@SelectProvider(type = UserProvider.class,method = "findAll")
	@Results(id = "userResultList",value = {
			@Result(column = "id",property = "id"),
			@Result(column = "name",property = "name",jdbcType = JdbcType.VARCHAR),
			@Result(column = "age",property = "age")
	})
	List<User> findAll(User user);
}
