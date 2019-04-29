package org.xiaobo.activity.sevice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaobo.activity.sevice.MessageService;
import org.xiaobo.activity.slave.entity.Message;
import org.xiaobo.activity.slave.repository.MessageRepository;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService{

	@Autowired
	MessageRepository messageRepository;
	
	@Override
	public Message saveMessage(Message message) {
		return messageRepository.save(message);
	}

	@Override
	public List<Message> findAll() {
		return messageRepository.findAll();
	}

}
