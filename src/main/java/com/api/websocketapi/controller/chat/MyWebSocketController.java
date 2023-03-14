package com.api.websocketapi.controller.chat;

import com.api.websocketapi.config.component.MyWebSocketHandler;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @author sion
 */
@Component
public class MyWebSocketController {

    @Resource
    private MyWebSocketHandler webSocketHandler;

    @RequestMapping("/send")
    public void sendMessage() throws IOException {
        // 发送WebSocket消息
        webSocketHandler.sendMessageToAll("Hello, WebSocket!");
    }
}