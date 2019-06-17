package org.xiaobo.stomp.message.stomp;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * Created by baiguantao on 2017/8/4.
 * STOMP监听类
 * 用于session注册 以及key值获取
 */
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {
    private static final Logger logger = LoggerFactory.getLogger(STOMPConnectEventListener.class);
    @Autowired
    SocketSessionRegistry webAgentSessionRegistry;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

        //login get from browser
        String agentId = sha.getNativeHeader("login").get(0);
        String sessionId = sha.getSessionId();
        logger.info("orderId is " + agentId);
        logger.info("sessionId is " + sessionId);
        webAgentSessionRegistry.registerSessionId(agentId, sessionId);

        for (Map.Entry<String, Set<String>> map : webAgentSessionRegistry.getAllSessionIds().entrySet()) {
            logger.info("-----------------");
            logger.info(map.getKey());
            logger.info("++++++++++++++++++++");
            for (String sId : map.getValue()) {
                logger.info(sId);
            }
            logger.info("------------------");
        }
    }
}
