package com.api.websocketapi.controller.chat;

import com.api.websocketapi.controller.chat.handler.WebSocketHandlerText;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @author sion
 */
@Component
@RequestMapping("/api/websocket")
public class MyWebSocketController {

    @Resource
    private WebSocketHandlerText webSocketHandler;

    @RequestMapping("/send")
    public void sendMessage() throws IOException {
        // 发送WebSocket消息
        webSocketHandler.sendMessageToAll("Hello, WebSocket!");
    }
}