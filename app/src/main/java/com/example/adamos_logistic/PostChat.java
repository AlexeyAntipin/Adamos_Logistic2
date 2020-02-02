package com.example.adamos_logistic;

public class PostChat {

    private String ID;

    private String Message;

    public PostChat(String ID, String Message) {
        this.ID = ID;
        this.Message = Message;
    }

    public String getID() {
        return ID;
    }

    public String getMessage() {
        return Message;
    }
}
