package org.xiaobo.websocket.usenettywebsocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.OnBinary;
import org.yeauty.annotation.OnClose;
import org.yeauty.annotation.OnError;
import org.yeauty.annotation.OnEvent;
import org.yeauty.annotation.OnMessage;
import org.yeauty.annotation.OnOpen;
import org.yeauty.annotation.ServerEndpoint;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
/**
   *    注意导入包路径为：org.yeauty.*
   *    自定义端口  默认80
 * 
 * @author xiaobo
 * @date 2019年5月15日
 */
@ServerEndpoint(port = 9090)
@Component
public class UseNettyWebSocket {
    
    private static final Logger logger = LoggerFactory.getLogger(UseNettyWebSocket.class);
    
    private static long sumCount=0;
    
    private Session session;
    
    public static Map<String,UseNettyWebSocket> socketMap=new HashMap<String,UseNettyWebSocket>();
    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws IOException {
        sumAdd();
        this.session=session;
        logger.info("new connection  to 9090  {}"+sumCount);

        String paramValue = parameterMap.getParameter("paramKey");
        socketMap.put(paramValue, this);
        session.setAttribute("paramKey", paramValue);
        session.sendText(paramValue+",欢迎您的到来");
        logger.info(paramValue+",欢迎您的到来");
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        sumSubtract();
        this.session=session;
        logger.info("one connection closed{}"+session.getAttribute("paramKey"));
    }

    @OnError
    public void onError(Session session, Throwable e) {
        this.session=session;
        e.printStackTrace();
        logger.error("连接异常", e);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        this.session=session;
        logger.info(message);
        session.sendText("Hello Netty!9090:"+message);
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
            logger.info(b+"");
        }
        session.sendBinary(bytes);
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent)evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    logger.info("read idle");
                    break;
                case WRITER_IDLE:
                    logger.info("write idle");
                    break;
                case ALL_IDLE:
                    logger.info("all idle");
                    break;
                default:
                    break;
            }
        }
    }
    
    /**
     * 数量累加
     */
    private static synchronized void sumAdd() {
        sumCount++;
    }
    /**
     * 数量累减
     */
    private static synchronized void sumSubtract() {
        sumCount--;
    }
    
    public void sendMessage(String message) {
        this.session.sendText("服务端发送："+message);
    }

}