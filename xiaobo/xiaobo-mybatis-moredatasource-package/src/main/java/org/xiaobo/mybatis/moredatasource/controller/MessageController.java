package org.xiaobo.mybatis.moredatasource.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xiaobo.mybatis.moredatasource.entity.Message;
import org.xiaobo.mybatis.moredatasource.service.MessageService;

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
	
	@PostMapping("/save_batch")
	@ResponseBody
	public String saveBatch(@RequestBody Message message) {
		List<Message> list=new ArrayList<Message>();
		Message  me=new Message();
		me.setName("con1");
		me.setContent("");
		list.add(me);
		Message  me1=new Message();
		me1.setName("con1");
		me1.setContent("");
		list.add(me1);
		boolean flag = messageService.saveBatch(list);
		return "save_batch";
	}
	
	@PostMapping("/update_bath")
	@ResponseBody
	public String updateBath(@RequestBody Message message) {
		List<Message> list=new ArrayList<Message>();
		Message  me=new Message();
		me.setId(4L);
		me.setName("con44");
		me.setContent("");
		list.add(me);
		Message me1=new Message();
		me1.setId(5L);
		me1.setName("con55");
		me1.setContent("");
		list.add(me1);
		Message me6=new Message();
		me6.setId(6L);
		me6.setName("con66");
		me6.setContent("");
		list.add(me6);
		 boolean flag = messageService.updateBatch(list);
		 return "update_bath";
	}
	
	@PostMapping("/update_sql")
	@ResponseBody
	public String updateSql(@RequestBody Message message) {
		Integer status = 0;
		Long[] orderList = new Long[] {1L,2L,3L};
		messageService.updateStatusByIds(status, orderList);
		return "update_sql";
	}
	
	@PostMapping("/remove_batch")
	@ResponseBody
	public String removeBatch(@RequestBody Message message) {
		List ids=new ArrayList();
		ids.add(6L);
		ids.add(7L);
		messageService.removeBatch(ids);
		return "remove_batch";
	}
}
