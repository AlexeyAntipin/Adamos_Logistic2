package com.example.adamos_logistic.Posts;

public class AddUser {
    private String api_key;
    private int order_id;
    private String ERROR_ID;

    public AddUser(String api_key, int order_id, String ERROR_ID) {
        this.api_key = api_key;
        this.order_id = order_id;
        this.ERROR_ID = ERROR_ID;
    }
}
