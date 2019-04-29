package org.xiaobo.mybatis.moredatasource.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaobo.mybatis.moredatasource.entity.User;
import org.xiaobo.mybatis.moredatasource.repository.primary.UserRepository;
import org.xiaobo.mybatis.moredatasource.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional(value=TxType.REQUIRED,rollbackOn = Exception.class)  
	public User saveUser(User user) {
		int save = userRepository.save(user);
		System.out.println(save);
		return user;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll(new User());
	}
	
	//@Transactional(value="test1TransactionManager",rollbackFor = Exception.class,timeout=36000)  
	//说明针对Exception异常也进行回滚，如果不标注，则Spring 默认只有抛出 RuntimeException才会回滚事务
	@Override
	@Transactional(value=TxType.REQUIRED,rollbackOn = Exception.class)  
    public void updateTransactional(User user) {
        try{
        	 userRepository.save(user);
        	userRepository.remove(user);
            int i= 1/0;
            userRepository.save(user);
        }catch(Exception e){
            throw e;   // 事物方法中，如果使用trycatch捕获异常后，需要将异常抛出，否则事物不回滚。
        }
    }

	@Override
	public User update(User user) {
		int update = userRepository.update(user);
		System.out.println(update);
		return user;
	}

	@Override
	public User remove(User user) {
		int remove = userRepository.remove(user);
		System.out.println(remove);
		return user ;
	}

}
