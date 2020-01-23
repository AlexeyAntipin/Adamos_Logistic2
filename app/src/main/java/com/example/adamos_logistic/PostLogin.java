package com.example.adamos_logistic;

public class PostLogin {

    private String EMAIL;

    private String PASSWORD;

    public PostLogin(String EMAIL, String PASSWORD) {
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
}
