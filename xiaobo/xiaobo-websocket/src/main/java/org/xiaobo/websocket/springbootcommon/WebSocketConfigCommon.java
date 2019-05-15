package org.xiaobo.websocket.springbootcommon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yeauty.standard.ServerEndpointExporter;

/**
 * 注意导入包路径为：javax.websocket.*
 * 开启WebSocket支持
 */
@Configuration
public class WebSocketConfigCommon {

    @Bean
    public ServerEndpointExporter serverEndpointExporterCommon() {
        return new ServerEndpointExporter();
    }
}