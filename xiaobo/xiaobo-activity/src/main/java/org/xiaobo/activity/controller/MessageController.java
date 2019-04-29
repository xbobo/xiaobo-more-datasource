package org.xiaobo.activity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xiaobo.activity.sevice.MessageService;
import org.xiaobo.activity.slave.entity.Message;

@Controller
@RequestMapping("/message")
public class MessageController {

	@Autowired
	MessageService messageService;
	
	@RequestMapping("/save")
	@ResponseBody
	public Message saveMessage(@RequestBody Message message) {
		return messageService.saveMessage(message);
	}
	
	@RequestMapping("/find")
	@ResponseBody
	public List<Message> findAll(@RequestBody Message message) {
		return messageService.findAll();
	}
}
