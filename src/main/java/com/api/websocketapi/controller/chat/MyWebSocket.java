package com.api.websocketapi.controller.chat;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
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
    public static List<MyWebSocket> webSockets = new CopyOnWriteArrayList<MyWebSocket>();

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
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String user,@PathParam("friendId") String friendId) {
        if (user == null || "".equals(user)) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        for (MyWebSocket myWebSocket : webSockets) {
            if (user.equals(myWebSocket.userId)) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
        }
        this.session = session;
        this.userId = user;
        this.friendId=friendId;
        webSockets.add(this);
        System.out.println("有新连接加入！");
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        webSockets.remove(this);
        System.out.println("有连接关闭！");
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") String user,@PathParam("friendId") String friendId) {
        System.out.println("来自" + user + "消息：" + message+"将发送给"+friendId);
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
     * @param message
     * @param friendId    uuid为空则推送全部人员
     */
    public static void pushMessage(String message, String friendId) {
        if (friendId == null || "".equals(friendId)) {
            System.out.println("用户不存在");
        } else {
            for (MyWebSocket myWebSocket : webSockets) {
                if (friendId.equals(myWebSocket.userId)) {
                    System.out.println("给用户"+myWebSocket.userId+"发送了"+message);
                    myWebSocket.sendMessage(message);
                }
            }
        }

    }
}