package org.xiaobo.mybatis.moredatasource.service;

import java.util.List;

import org.xiaobo.mybatis.moredatasource.entity.Message;


public interface MessageService {

	Message save(Message message);
	
	List<Message> findAll();

	void updateTransactional(Message message);

	Message update(Message message);

	Message remove(Message message);
	
	boolean saveBatch(List<Message> list);
	
	boolean updateBatch(List<Message> list);
	
	boolean removeBatch(List ids);

	boolean updateStatusByIds(Integer status, Long[] ids);
}
