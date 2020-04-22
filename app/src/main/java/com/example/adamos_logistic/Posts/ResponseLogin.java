package com.example.adamos_logistic.Posts;

public class ResponseLogin {
    private String api_key;
    private int role;
    public String ERROR_ID;

    public ResponseLogin(String api_key, int role, String ERROR_ID) {
        this.api_key = api_key;
        this.role = role;
        this.ERROR_ID = ERROR_ID;
    }

    public String getApi_key() {
        return api_key;
    }

    public int getRole() {
        return role;
    }

    public String getERROR_ID() {
        return ERROR_ID;
    }
}
