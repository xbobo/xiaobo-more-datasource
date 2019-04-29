package org.xiaobo.mybatis.moredatasource.aop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xiaobo.mybatis.moredatasource.aop.entity.Message;
import org.xiaobo.mybatis.moredatasource.aop.service.secondary.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	MessageService messageService;
	
	@PostMapping("/save")
	@ResponseBody
	public Message saveMessage(@RequestBody Message message) {
		return messageService.save(message);
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Message update(@RequestBody Message message) {
		return messageService.update(message);
	}
	
	@PostMapping("/remove")
	@ResponseBody
	public Message remove(@RequestBody Message message) {
		return messageService.remove(message);
	}
	
	@PostMapping("/find")
	@ResponseBody
	public List<Message> findAll(@RequestBody Message message) {
		return messageService.findAll();
	}
	
	@PostMapping("/test")
	@ResponseBody
	public String test(@RequestBody Message message) {
		 messageService.updateTransactional(message);
		 return "success";
	}
}
