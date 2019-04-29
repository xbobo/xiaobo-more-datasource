package org.xiaobo.mybatis.moredatasource.aop.service.secondary.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaobo.mybatis.moredatasource.aop.entity.Message;
import org.xiaobo.mybatis.moredatasource.aop.repository.MessageRepository;
import org.xiaobo.mybatis.moredatasource.aop.service.secondary.MessageService;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageRepository messageRepository;

	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	public Message save(Message message) {
		int save = messageRepository.save(message);
		System.out.println(save);
		return message;
	}

	@Override
	public List<Message> findAll() {
		return messageRepository.findAll(new Message());
	}

	// @Transactional(value="test1TransactionManager",rollbackFor =
	// Exception.class,timeout=36000)
	// 说明针对Exception异常也进行回滚，如果不标注，则Spring 默认只有抛出 RuntimeException才会回滚事务
	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	public void updateTransactional(Message message) {
		try {
			messageRepository.remove(message);
			int i = 1 / 0;
			messageRepository.save(message);
		} catch (Exception e) {
			throw e; // 事物方法中，如果使用trycatch捕获异常后，需要将异常抛出，否则事物不回滚。
		}

	}

	@Override
	public Message update(Message message) {
		  int update = messageRepository.update(message);
		  System.out.println(update);
		return message;
	}

	@Override
	public Message remove(Message message) {
		  int remove = messageRepository.remove(message);
		  System.out.println(remove);
		return message;
	}

}
