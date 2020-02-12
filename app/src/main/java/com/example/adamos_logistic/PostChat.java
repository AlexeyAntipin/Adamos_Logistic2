package com.example.adamos_logistic;

public class PostChat {

    private String ORDER_ID;

    private String MESSAGE;

    private boolean IS_REG;

    public PostChat(String ORDER_ID, String MESSAGE, boolean IS_REG) {
        this.ORDER_ID = ORDER_ID;
        this.MESSAGE = MESSAGE;
        this.IS_REG = IS_REG;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public String getMessage() {
        return MESSAGE;
    }
}
