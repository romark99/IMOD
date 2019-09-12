package com.brest.bstu.po1.imod.model;

public class SimpleResponse {

    public SimpleResponse(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SimpleResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
