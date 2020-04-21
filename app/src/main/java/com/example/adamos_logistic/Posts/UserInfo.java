package com.example.adamos_logistic.Posts;

public class UserInfo {
    private int role;
    private String avatar;
    private String name;
    private int id;

    public UserInfo(int role, String avatar, String name, int id) {
        this.role = role;
        this.name = name;
        this.avatar = avatar;
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }
}
