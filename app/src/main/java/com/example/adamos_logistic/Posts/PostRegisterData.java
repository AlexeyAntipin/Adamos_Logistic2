package com.example.adamos_logistic.Posts;

public class PostRegisterData {
    private String name;
    private String email;
    private String password;

    public PostRegisterData(String email, String password, String name) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
