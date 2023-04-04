package com.api.websocketapi.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Friendschema)实体类
 *
 * @author makejava
 * @since 2023-04-04 23:53:42
 */
public class Friendschema implements Serializable {
    private static final long serialVersionUID = -32367886393085156L;

    private Integer id;

    private Integer userid;

    private Integer friendid;

    private Integer state;

    private Date time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getFriendid() {
        return friendid;
    }

    public void setFriendid(Integer friendid) {
        this.friendid = friendid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}

