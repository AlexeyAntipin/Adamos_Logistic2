package com.example.adamos_logistic.Posts;

public class AddResponseBodyOrders {
    private int order_id;
    private String ERROR_ID;

    public AddResponseBodyOrders(int order_id, String ERROR_ID) {
        this.order_id = order_id;
        this.ERROR_ID = ERROR_ID;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getERROR_ID() {
        return ERROR_ID;
    }
}
