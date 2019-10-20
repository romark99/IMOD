package com.brest.bstu.po1.imod.model;

public class User {

    private String nickname;

    public User(String nickname) {
        this.nickname = nickname;
    }

    public User() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
