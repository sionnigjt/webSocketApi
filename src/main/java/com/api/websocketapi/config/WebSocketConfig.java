package com.api.websocketapi.config;

import com.api.websocketapi.controller.chat.handler.WebSocketHandlerText;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private WebSocketHandlerText webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 添加一个WebSocket端点，客户端可以通过ws://localhost:8080/websocket连接到该端点
        registry.addHandler(webSocketHandler, "/api/websocket").setAllowedOrigins("*")
//                 添加WebSocket拦截器
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
