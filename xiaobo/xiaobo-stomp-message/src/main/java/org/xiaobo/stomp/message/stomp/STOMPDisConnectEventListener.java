package org.xiaobo.stomp.message.stomp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class STOMPDisConnectEventListener implements ApplicationListener<SessionDisconnectEvent> {
    private static final Logger logger = LoggerFactory.getLogger(STOMPDisConnectEventListener.class);
    @Autowired
    SocketSessionRegistry webAgentSessionRegistry;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        logger.info("断开的session");
        logger.info(event.getSessionId());
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        logger.info(sha.getCommand().name());
        if (sha.getCommand() == StompCommand.DISCONNECT) {
            logger.info("断开了");
        }
        webAgentSessionRegistry.removeBySessionId(event.getSessionId());
    }
}
