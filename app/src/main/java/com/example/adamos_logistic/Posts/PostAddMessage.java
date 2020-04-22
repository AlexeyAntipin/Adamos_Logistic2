package com.example.adamos_logistic.Posts;

public class PostAddMessage {
    private String api_key;
    private int order_id;
    private String value;
    private int attachment_id;

    public PostAddMessage(String api_key, int order_id, String value, int attachment_id) {
        this.api_key = api_key;
        this.order_id = order_id;
        this.value = value;
        this.attachment_id = attachment_id;
    }
}
