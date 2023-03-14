package com.api.websocketapi.config.webSocket;

import com.api.websocketapi.config.component.MyWebSocketHandler;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @author sion
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private MyWebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 添加一个WebSocket端点，客户端可以通过ws://localhost:8080/websocket连接到该端点
        registry.addHandler(webSocketHandler, "/websocket").setAllowedOrigins("*");
                // 添加WebSocket拦截器
//                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
