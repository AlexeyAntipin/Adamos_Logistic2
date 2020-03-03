package com.example.adamos_logistic.Posts;

public class PostChat {

    private String USER_UID;

    private String ORDER_UID;

    private String MESSAGE;

    private String FILE_NAME;

    private boolean IS_REG;

    public PostChat(
            String USER_UID,
            String ORDER_UID,
            String MESSAGE,
            String FILE_NAME,
            boolean IS_REG) {

        this.USER_UID = USER_UID;
        this.ORDER_UID = ORDER_UID;
        this.MESSAGE = MESSAGE;
        this.FILE_NAME = FILE_NAME;
        this.IS_REG = IS_REG;
    }

    public String getUSER_UID() {
        return USER_UID;
    }

    public String getORDER_UID() {
        return ORDER_UID;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public boolean getIS_REG() {
        return IS_REG;
    }
}
