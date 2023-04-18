package com.api.websocketapi.entity;

import java.util.Date;

public class MessageContent {

    private Integer id;


    private Integer sendid;

    private Integer sendtoid;

    private String content;

    private Integer type;

    private Date time;

    private Integer state;
    private  String img_url;

    public MessageContent() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendid() {
        return sendid;
    }

    public void setSendid(Integer sendid) {
        this.sendid = sendid;
    }

    public Integer getSendtoid() {
        return sendtoid;
    }

    public void setSendtoid(Integer sendtoid) {
        this.sendtoid = sendtoid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
