package com.example.adamos_logistic;

public class Order {

    private String order_name;

    private String order_id;

    private String order_status;

    public Order(String order_name, String order_id, String order_status) {
        this.order_name = order_name;
        this.order_id = order_id;
        this.order_status = order_status;
    }

    public String getOrder_name() {
        return order_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_status() {
        return order_status;
    }
}
