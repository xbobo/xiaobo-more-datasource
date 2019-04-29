package org.xiaobo.activity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xiaobo.activity.master.entity.User;
import org.xiaobo.activity.sevice.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping("/save")
	@ResponseBody
	public User saveMessage(@RequestBody User user) {
		User saveUser = userService.saveUser(user);
		return saveUser;
	}
	
	@RequestMapping("/find")
	@ResponseBody
	public List<User> findAll(@RequestBody User user) {
		List<User> findAll = userService.findAll();
		return findAll;
	}
}
