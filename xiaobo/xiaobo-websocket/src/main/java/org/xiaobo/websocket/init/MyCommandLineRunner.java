 package org.xiaobo.websocket.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.xiaobo.websocket.usenettywebsocket.UseNettyWebSocket;

@Component
public class MyCommandLineRunner implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(MyCommandLineRunner.class);
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("MyCommandLineRunner start ...");
    }
    
    public void init() {
        
    }
    
    @RabbitListener(queuesToDeclare = @Queue("pay_websocket_test"))
    public void update_orderStatus(String message) {
        try {
            logger.info("通知前端websoket更新待支付的订单号为:" + message);
            UseNettyWebSocket myNettyWebSocket = UseNettyWebSocket.socketMap.get(message);
            if(myNettyWebSocket!=null) {
                myNettyWebSocket.sendMessage("linstner:"+message);
                logger.info("通知前端websoket更新待支付订单更新为支付成功");
            }else {
                logger.info("通知前端websoket更新待支付订单，未找到"+myNettyWebSocket);
            }
        } catch (Exception ex) {
            logger.error("更新出问题了", ex);
        }
    }
}
