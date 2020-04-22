package com.example.adamos_logistic.Posts;

public class GetMessages {
    private String attachment_id;
    private int order_id;
    private String value;
    private int user_id;
    private String time;

    public GetMessages(String attachment_id, int order_id, String value, int user_id, String time) {
        this.attachment_id = attachment_id;
        this.order_id = order_id;
        this.value = value;
        this.user_id = user_id;
        this.time = time;
    }

    public String getAttachment_id() {
        return attachment_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getValue() {
        return value;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTime() {
        return time;
    }
}
