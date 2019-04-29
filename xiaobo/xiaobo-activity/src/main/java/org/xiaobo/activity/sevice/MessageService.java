package org.xiaobo.activity.sevice;

import java.util.List;

import org.xiaobo.activity.slave.entity.Message;

public interface MessageService {

	Message saveMessage(Message message);
	
	List<Message> findAll();
}
