package com.example.adamos_logistic.Posts;

public class UserInfo {

    private String role;
    private String avatar;
    private String name;
    private int id;

    public UserInfo(String role, String avatar, String name, int id) {
            this.role = role;
            this.avatar = avatar;
            this.name = name;
            this.id = id;
            }

    public String getRole() {
            return role;
            }

    public String getAvatar() {
            return avatar;
            }

    public String getName() {
            return name;
            }

    public int getId() {
            return id;
    }
}