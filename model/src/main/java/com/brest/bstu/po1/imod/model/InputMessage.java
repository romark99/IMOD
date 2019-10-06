package com.brest.bstu.po1.imod.model;

public class InputMessage {

    private String message;

    private String nickname;

    private String time;

    public InputMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "InputMessage{" +
               "message='" + message + '\'' +
               ", nickname='" + nickname + '\'' +
               ", time='" + time + '\'' +
               '}';
    }
}
