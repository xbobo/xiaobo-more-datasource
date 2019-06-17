package org.xiaobo.websocket.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xiaobo.websocket.conf.Result;
import org.xiaobo.websocket.dto.QueryDTO;
import org.xiaobo.websocket.usenettywebsocket.UseNettyWebSocket;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/test")
public class TestController {
    
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @PostMapping("/test")
    @ResponseBody
    public Result orderLoginByCode(@RequestBody QueryDTO queryDTO, HttpServletRequest request) {
    	logger.info("查询参数："+JSONObject.toJSONString(queryDTO));
    	String queryId = queryDTO.getId();
    	UseNettyWebSocket myNettyWebSocket = UseNettyWebSocket.socketMap.get(queryId);
        if(myNettyWebSocket!=null) {
        	myNettyWebSocket.sendMessage(queryId);
        }else {
        	logger.info(queryId+"对应的MyNettyWebSocket找不到");
        }
        return Result.get(Result.OK, "", JSONObject.toJSONString(queryDTO));
    }
    
    @PostMapping("/rb_send")
    @ResponseBody
    public Result rbSend(@RequestBody QueryDTO queryDTO, HttpServletRequest request) {
    	logger.info("查询参数："+JSONObject.toJSONString(queryDTO));
    	String queryId = queryDTO.getId();
        rabbitTemplate.convertAndSend("pay_websocket_test", queryId);
        return Result.get(Result.OK, "", JSONObject.toJSONString(queryDTO));
    }
}
