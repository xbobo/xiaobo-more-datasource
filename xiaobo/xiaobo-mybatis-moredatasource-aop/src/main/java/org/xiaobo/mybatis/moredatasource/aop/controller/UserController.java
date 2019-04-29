package org.xiaobo.mybatis.moredatasource.aop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xiaobo.mybatis.moredatasource.aop.entity.User;
import org.xiaobo.mybatis.moredatasource.aop.service.primary.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/save")
	@ResponseBody
	public User saveMessage(@RequestBody User user) {
		User saveUser = userService.saveUser(user);
		return saveUser;
	}
	
	@PostMapping("/update")
	@ResponseBody
	public User update(@RequestBody User user) {
		User saveUser = userService.update(user);
		return saveUser;
	}
	
	@PostMapping("/remove")
	@ResponseBody
	public User remove(@RequestBody User user) {
		User saveUser = userService.remove(user);
		return saveUser;
	}
	
	@PostMapping("/find")
	@ResponseBody
	public List<User> findAll(@RequestBody User user) {
		List<User> findAll = userService.findAll();
		return findAll;
	}
	
	@PostMapping("/test")
	@ResponseBody
	public String test(@RequestBody User user) {
		userService.updateTransactional(user);
		 return "success";
	}
}
