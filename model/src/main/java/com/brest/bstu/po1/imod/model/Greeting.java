package com.brest.bstu.po1.imod.model;

import java.sql.Timestamp;

public class Greeting {

    private String content;

    private Integer id;

    private Timestamp datetime;

    private String datetime_str;

    private Integer room;

    private String nickname;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDatetime_str() {
        return datetime_str;
    }

    public void setDatetime_str(String datetime_str) {
        this.datetime_str = datetime_str;
    }

    @Override
    public String toString() {
        return "Greeting{" +
               "content='" + content + '\'' +
               ", id=" + id +
               ", datetime=" + datetime +
               ", datetime_str='" + datetime_str + '\'' +
               ", room=" + room +
               ", nickname='" + nickname + '\'' +
               '}';
    }
}
