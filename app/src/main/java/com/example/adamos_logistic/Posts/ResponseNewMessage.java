package com.example.adamos_logistic.Posts;

public class ResponseNewMessage {
    public String ERROR_ID;

    public ResponseNewMessage(String ERROR_ID) {
        this.ERROR_ID = ERROR_ID;
    }

    public String getERROR_ID() {
        return ERROR_ID;
    }
}
