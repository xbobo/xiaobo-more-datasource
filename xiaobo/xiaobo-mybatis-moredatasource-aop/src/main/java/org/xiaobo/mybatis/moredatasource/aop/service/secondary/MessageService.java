package org.xiaobo.mybatis.moredatasource.aop.service.secondary;

import java.util.List;

import org.xiaobo.mybatis.moredatasource.aop.entity.Message;

public interface MessageService {

	Message save(Message message);
	
	List<Message> findAll();

	void updateTransactional(Message message);

	Message update(Message message);

	Message remove(Message message);
}
