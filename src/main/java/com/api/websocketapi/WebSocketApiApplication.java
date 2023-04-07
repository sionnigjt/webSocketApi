package com.api.websocketapi;

import com.api.websocketapi.controller.chat.MyWebSocket;
import com.api.websocketapi.service.MessageschemaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author sion
 */
@SpringBootApplication
public class WebSocketApiApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(WebSocketApiApplication.class);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        //解决WebSocket不能注入的问题
        MyWebSocket.setApplicationContext(configurableApplicationContext);
    }


}
