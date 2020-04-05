package com.example.adamos_logistic.Posts;

public class GetOrders {
    private int order_ID;
    private String name;
    private String date;
    private info orderInfo = new info();

    public int get_order_ID() {
        return order_ID;
    }

    public String get_name() {
        return name;
    }

    public String get_date() {
        return date;
    }

    public int getOrderInfo() {
        return orderInfo.getSum();
    }
}
