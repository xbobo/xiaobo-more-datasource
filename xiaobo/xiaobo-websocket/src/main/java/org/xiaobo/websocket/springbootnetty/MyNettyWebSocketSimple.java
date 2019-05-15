package org.xiaobo.websocket.springbootnetty;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.yeauty.annotation.OnClose;
import org.yeauty.annotation.OnError;
import org.yeauty.annotation.OnOpen;
import org.yeauty.annotation.ServerEndpoint;
import org.yeauty.pojo.Session;

import io.netty.handler.codec.http.HttpHeaders;
/**
 * 注意导入包路径为：org.yeauty.*
 * 
 * 自定义端口  默认80
 * 
 * @author xiaobo
 * @date 2019年5月15日
 */
@ServerEndpoint(port =8081 )
@Component
public class MyNettyWebSocketSimple {

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers) throws IOException {
        System.out.println("new connection");
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("one connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @org.yeauty.annotation.OnMessage
    public void OnMessage(Session session, String message) {
        System.out.println(message);
        session.sendText("Hello Netty!8081");
    }
}
