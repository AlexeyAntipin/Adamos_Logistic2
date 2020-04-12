package com.example.adamos_logistic.Posts;

public class ResponseLogin {
    private String api_key;
    private String role;
    private String ERROR_ID;

    public ResponseLogin(String api_key, String role, String ERROR_ID) {
        this.api_key = api_key;
        this.role = role;
        this.ERROR_ID = ERROR_ID;
    }
}
