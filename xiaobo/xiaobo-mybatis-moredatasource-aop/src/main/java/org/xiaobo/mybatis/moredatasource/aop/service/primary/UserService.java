package org.xiaobo.mybatis.moredatasource.aop.service.primary;

import java.util.List;

import org.xiaobo.mybatis.moredatasource.aop.entity.User;


public interface UserService {

	User saveUser(User user);
	
	List<User> findAll();

	void updateTransactional(User user);

	User update(User user);

	User remove(User user);
	
}
