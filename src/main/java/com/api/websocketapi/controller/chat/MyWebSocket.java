package com.api.websocketapi.controller.chat;

import com.api.websocketapi.dao.MessageschemaDao;
import com.api.websocketapi.entity.Messageschema;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author sion
 */
@Component
@ServerEndpoint(value = "/api/websocket/{userId}/{friendId}")
public class MyWebSocket {
    /**
     * 所有的对象，每次连接建立，都会将我们自己定义的MyWebSocket存放到List中，
     */
    public static List<MyWebSocket> webSockets = new CopyOnWriteArrayList<>();

    /**
     * 会话，与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 每个会话的用户
     */
    private String userId;
    private String friendId;
    /**
     * 服务对象
     */

    //此处是解决无法注入的关键

    private static ApplicationContext applicationContext;
    //你要注入的service或者dao

    private MessageschemaDao messageschemaDao;
    public static void setApplicationContext(ApplicationContext applicationContext) {
        MyWebSocket.applicationContext = applicationContext;
    }


    @Autowired
    public void initMessageschemaDao(MessageschemaDao messageschemaDao) {
        this.messageschemaDao = messageschemaDao;
    }

    /**
     * 建立连接
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId, @PathParam("friendId") String friendId) {
        if (userId == null || "".equals(userId)) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        for (MyWebSocket myWebSocket : webSockets) {
            if (userId.equals(myWebSocket.userId)) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
        }
        this.session = session;
        this.userId = userId;
        this.friendId = friendId;
        webSockets.add(this);
        messageschemaDao = applicationContext.getBean(MessageschemaDao.class);
        System.out.println(userId + "加入！" + this.messageschemaDao);

    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        webSockets.remove(this);
        System.out.println(this.userId + "退出！");
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("来自" + userId + "消息：" + message + "将发送给" + friendId);
        Messageschema messageschema = new Messageschema(userId, friendId, message, 0);
//        messageschemaService.insert(messageschema);
        pushMessage(message, friendId);
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息推送
     *
     * @param friendId uuid为空则推送全部人员
     */
    public static void pushMessage(String message, String friendId) {
        if (friendId == null || "".equals(friendId)) {
            System.out.println("用户不存在");
        } else {
            for (MyWebSocket myWebSocket : webSockets) {
                if (friendId.equals(myWebSocket.userId)) {
                    System.out.println("给用户" + myWebSocket.userId + "发送了" + message);
                    myWebSocket.sendMessage(message);
                }
            }
        }

    }
}