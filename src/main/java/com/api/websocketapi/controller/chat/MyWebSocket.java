package com.api.websocketapi.controller.chat;

import com.api.websocketapi.dao.MessageschemaDao;
import com.api.websocketapi.dao.UserschemaDao;
import com.api.websocketapi.entity.MessageContent;
import com.api.websocketapi.entity.Messageschema;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import net.sf.json.JSONObject;
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
    private  String imgUrl;
    private Boolean isInit=false;

    
    /**
     * 服务对象
     */

    //解决无法注入

    private static ApplicationContext applicationContext;
    //你要注入的service或者dao

    private MessageschemaDao messageschemaDao;
    private static   UserschemaDao userschemaDao;
    public static void setApplicationContext(ApplicationContext applicationContext) {
        MyWebSocket.applicationContext = applicationContext;
    }


    @Autowired
    public void initMessageschemaDao(MessageschemaDao messageschemaDao) {
        this.messageschemaDao = messageschemaDao;

    }
    @Autowired
    public void initUserschemaDao(UserschemaDao userschemaDao) {
        MyWebSocket.userschemaDao = userschemaDao;

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
        this.isInit=false;
        webSockets.add(this);
        messageschemaDao = applicationContext.getBean(MessageschemaDao.class);
        System.out.println("用户"+userId + "加入！");

    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        System.out.println(this.userId + "退出！");
        webSockets.remove(this);
        isInit=false;
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     */
    @OnMessage
    public void onMessage(String message) {
        //初始化数据
        if ("init".equals(message) && !isInit){
            isInit=true;
            List<MessageContent> messageschemaList = this.messageschemaDao.selectUnreadListBySendIds(Integer.valueOf(friendId), Integer.valueOf(userId));
            for (MessageContent messageschema : messageschemaList) {
                try {

                    JSONObject result = new JSONObject();
                    result.put("message", messageschema.getContent());
                    result.put("imgUrl", messageschema.getImg_url());
                    Integer type=2;
                    if (this.userId.equals(messageschema.getSendid()+"")){
                        type=1;
                    }
                    result.put("type",type);
                    session.getBasicRemote().sendText(result.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //防止消息表中无imgUrl
            this.imgUrl = userschemaDao.getUrlById(Integer.valueOf(friendId));

        }
        else {
            System.out.println("来自" + userId + "消息：" + message + "将发送给" + friendId);
//            Messageschema messages = new Messageschema(userId, friendId, message, 0);
//        messageschemaService.insert(messageschema);
            setMessage(userId,friendId, message);
            pushMessage(message, friendId);
        }


    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        try {

            JSONObject result = new JSONObject();
            result.put("message", message);
            result.put("imgUrl",this.imgUrl);
            System.out.println("给用户" + this.userId + "发送了" + message);
            this.session.getBasicRemote().sendText(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息推送
     *
     * @param friendId uuid为空则推送全部人员
     */
    public static void pushMessage(String content, String friendId) {
        if (friendId == null || "".equals(friendId)) {
            System.out.println("用户不存在");
        } else {
            for (MyWebSocket myWebSocket : webSockets) {
                if (friendId.equals(myWebSocket.userId)) {

                    JSONObject json = JSONObject.fromObject(content);
                    System.out.println(json+json.toString()+content);
                    myWebSocket.sendMessage(json.getString("message"));
                }
            }
        }

    }
    private void  setMessage(String sendId,String sendToId ,String content){
        JSONObject json = JSONObject.fromObject(content);
        String message=json.getString("message");
        Integer type=json.getInt("type");
        Messageschema messages = new Messageschema(sendId, sendToId, message,type,0);
        messageschemaDao.insert(messages);
    }
}