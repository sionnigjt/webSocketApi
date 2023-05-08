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
    private String imgUrl;
    private Boolean isInit = false;
    private Boolean isSend = false;

    /**
     * 服务对象
     */

    //解决无法注入

    private static ApplicationContext applicationContext;
    //你要注入的service或者dao

    private MessageschemaDao messageschemaDao;
    private static UserschemaDao userschemaDao;

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
        this.isInit = false;

        webSockets.add(this);
        messageschemaDao = applicationContext.getBean(MessageschemaDao.class);
        System.out.println("用户" + userId + "加入！");

    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        System.out.println(this.userId + "退出！");
        webSockets.remove(this);
        isInit = false;
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     */
    @OnMessage
    public void onMessage(String message) {
        //初始化数据
        if ("init".equals(message) && !isInit) {
            isInit = true;
            List<MessageContent> messageschemaList = this.messageschemaDao.selectListBySendIds(Integer.valueOf(friendId), Integer.valueOf(userId));
            for (MessageContent messageschema : messageschemaList) {
                try {
                    //未读信息
                    if (messageschema.getState()==0){
                        messageschemaDao.changeSateToRead(messageschema.getId());
                    }
                    JSONObject result = new JSONObject();
                    result.put("message", messageschema.getContent());
                    result.put("imgUrl", messageschema.getImg_url());
                    Integer type = 2;
                    if (this.userId.equals(messageschema.getSendid() + "")) {
                        type = 1;
                    }
                    result.put("type", type);
                    result.put("viewStyle",messageschema.getType());
                    session.getBasicRemote().sendText(result.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //防止消息表中无imgUrl
            this.imgUrl = userschemaDao.getUrlById(Integer.valueOf(friendId));

        } else {
            System.out.println("来自" + userId + "消息：" + message + "将发送给" + friendId);
//            Messageschema messages = new Messageschema(userId, friendId, message, 0);
//        messageschemaService.insert(messageschema);

            pushMessage(message, friendId);
            if (this.isSend==true){
                setMessage(userId, friendId, message, 1);
                this.isSend=false;
            }
            else {

                setMessage(userId, friendId, message, 0);
            }

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
            result.put("imgUrl", this.imgUrl);
            if (isImageURL(message)){
                result.put("viewStyle",1);
            }
            else {
                result.put("viewStyle",0);
            }

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
    public void pushMessage(String content, String friendId) {

        if (friendId == null || "".equals(friendId)) {
            System.out.println("用户不存在");

        } else {
            for (MyWebSocket myWebSocket : webSockets) {
                if (friendId.equals(myWebSocket.userId)) {
                    this.isSend = true;
                    JSONObject json = JSONObject.fromObject(content);
                    System.out.println(json + json.toString() + content);
                    myWebSocket.sendMessage(json.getString("message"));

                }
            }
        }

    }

    private void setMessage(String sendId, String sendToId, String content, Integer sate) {
        JSONObject json = JSONObject.fromObject(content);
        String message = json.getString("message");
        Integer type = json.getInt("type");
        Messageschema messages = new Messageschema(sendId, sendToId, message, type, sate);
        messageschemaDao.insert(messages);
    }
    public boolean isImageURL(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        // 检查 URL 是否以 http://sion.link:9000/ 开头
        if (!url.startsWith("http://sion.link:9000/")) {
            return false;
        }

        // 获取 URL 的文件扩展名
        String ext = url.substring(url.lastIndexOf(".") + 1);

        // 检查扩展名是否为图片格式
        if (ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
            return true;
        }

        return false;
    }
}