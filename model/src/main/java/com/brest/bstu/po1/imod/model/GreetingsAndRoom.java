package com.brest.bstu.po1.imod.model;

import java.util.Arrays;

public class GreetingsAndRoom {

    private Greeting[] greetings;

    private Integer room;

    public Greeting[] getGreetings() {
        return greetings;
    }

    public void setGreetings(Greeting[] greetings) {
        this.greetings = greetings;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "GreetingsAndRoom{" +
               "greetings=" + Arrays.toString(greetings) +
               ", room=" + room +
               '}';
    }
}
