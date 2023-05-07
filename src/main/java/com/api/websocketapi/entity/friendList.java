package com.api.websocketapi.entity;

import java.util.Date;

/**
 * @author sion
 */
public class friendList {

    private Integer id;

    private Integer sendId;

    private Integer sendToId;
    private String name;

    private String imgUrl;
    private Date time;
    private String content;
    private Integer unread;
    public friendList() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public Integer getSendToId() {
        return sendToId;
    }

    public void setSendToId(Integer sendToId) {
        this.sendToId = sendToId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }
}
