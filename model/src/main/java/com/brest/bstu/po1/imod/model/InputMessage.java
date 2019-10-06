package com.brest.bstu.po1.imod.model;

public class InputMessage {

    private String message;

    private String nickname = "Roma";

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

    @Override
    public String toString() {
        return "InputMessage{" +
               "message='" + message + '\'' +
               ", nickname='" + nickname + '\'' +
               '}';
    }
}
