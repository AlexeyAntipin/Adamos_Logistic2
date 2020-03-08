package com.example.adamos_logistic;

public class Message {

    private String message;
    private String time;
    private boolean from_user;
    private String id;

    public Message(String message, String time, boolean from_user, String id) {
        this.message = message;
        this.time = time;
        this.from_user = from_user;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public boolean isFrom_user() {
        return from_user;
    }
}
