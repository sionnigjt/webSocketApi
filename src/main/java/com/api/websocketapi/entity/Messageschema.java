package com.api.websocketapi.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Messageschema)实体类
 *
 * @author makejava
 * @since 2023-04-07 09:52:26
 */
public class Messageschema implements Serializable {
    private static final long serialVersionUID = 338613582633036710L;

    private Integer id;

    private Integer sendid;

    private Integer sendtoid;

    private String content;

    private Integer type;

    private Date time;

    private Integer state;


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

}

