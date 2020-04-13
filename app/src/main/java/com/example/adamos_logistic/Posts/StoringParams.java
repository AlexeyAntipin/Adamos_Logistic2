package com.example.adamos_logistic.Posts;

public class StoringParams {
    public static String api_key;
    public static int order_id;
    public static int role;

    public static String getApi_key() {
        return api_key;
    }

    public static int getOrder_id() {
        return order_id;
    }

    public static int getRole() {
        return role;
    }

    public static void setApi_key(String api_key) {
        StoringParams.api_key = api_key;
    }

    public static void setOrder_id(int order_id) {
        StoringParams.order_id = order_id;
    }

    public static void setRole(int role) {
        StoringParams.role = role;
    }
}
