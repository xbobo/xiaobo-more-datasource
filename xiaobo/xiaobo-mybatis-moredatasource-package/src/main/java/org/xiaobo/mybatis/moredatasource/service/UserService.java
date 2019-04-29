package org.xiaobo.mybatis.moredatasource.service;

import java.util.List;

import org.xiaobo.mybatis.moredatasource.entity.User;


public interface UserService {

	User saveUser(User user);
	
	List<User> findAll();

	void updateTransactional(User user);

	User update(User user);

	User remove(User user);
	
}
