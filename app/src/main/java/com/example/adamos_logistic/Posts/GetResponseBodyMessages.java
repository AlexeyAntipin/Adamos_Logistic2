package com.example.adamos_logistic.Posts;

public class GetResponseBodyMessages {
    private String attachment_id;
    private int order_id;
    private String text;
    private String time;

    public GetResponseBodyMessages(String attachment_id, int order_id, String text, String time) {
        this.attachment_id = attachment_id;
        this.order_id = order_id;
        this.text = text;
        this.time = time;
    }

    public String getAttachment_id() {
        return attachment_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }
}
