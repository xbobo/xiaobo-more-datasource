package org.xiaobo.activity.sevice;

import java.util.List;

import org.xiaobo.activity.master.entity.User;

public interface UserService {

	User saveUser(User user);
	
	List<User> findAll();
	
}
