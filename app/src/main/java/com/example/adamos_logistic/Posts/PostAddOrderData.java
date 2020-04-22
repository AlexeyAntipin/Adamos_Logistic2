package com.example.adamos_logistic.Posts;

public class PostAddOrderData {
    private String api_key;
    private int step;

    public PostAddOrderData(String api_key, int step) {
        this.api_key = api_key;
        this.step = step;
    }
}
