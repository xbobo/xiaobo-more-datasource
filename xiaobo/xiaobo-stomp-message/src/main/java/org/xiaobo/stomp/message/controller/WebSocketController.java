package org.xiaobo.stomp.message.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xiaobo.stomp.message.stomp.SocketSessionRegistry;

@Controller
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    public SimpMessagingTemplate template;


    @Autowired
    private SocketSessionRegistry webAgentSessionRegistry;



    @RequestMapping(value = "hello")
    @ResponseBody
    public String getOAuth2UserInfo(HttpServletRequest request, HttpServletResponse response) {
        return "hello world";
    }

    @RabbitListener(queuesToDeclare = @Queue("pay_notice"))
    public void orderDivideClass(String message) {
        logger.info("----------recommend_reward--------------");
        logger.info("------------------------");
        logger.info("receive message:" + message);
        //String id = redisTemplate.opsForValue().get("pay_notice_" + message);

        try {
            String sessionId = webAgentSessionRegistry.getSessionIds(message).stream().findFirst().get();
            if (sessionId == null) {
                return;
            }
            logger.info(sessionId);
            template.convertAndSendToUser(message, "notice", true, createHeaders(sessionId));
        } catch (Exception ex) {
            logger.error("发送失败", ex);
        }
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

}
