package com.example.adamos_logistic.Posts;

import java.util.List;

public class OrderGetResult {

    private List<GetResponseBodyOrders> ORDERS;

    public OrderGetResult(List<GetResponseBodyOrders> ORDERS) {
        this.ORDERS = ORDERS;
    }

    public List<GetResponseBodyOrders> getList() {
        return ORDERS;
    }

    public String getOrderName() {
        String str = "";
        for (GetResponseBodyOrders getResponseBodyOrders : ORDERS) {
            if (getResponseBodyOrders.getName().equals("name")
            )
                str = getResponseBodyOrders.getName();
        }
        return str;
    }

    public String getOrderStatus() {
        String str = "";
        for (GetResponseBodyOrders getResponseBodyOrders : ORDERS) {
            if (getResponseBodyOrders.getName().equals("order_status")
            )
                str = getResponseBodyOrders.getOrderStatus();
        }
        return str;
    }

    public String getTimeCreated() {
        String str = "";
        for (GetResponseBodyOrders getResponseBodyOrders : ORDERS) {
            if (getResponseBodyOrders.getName().equals("time_created"))
                str = getResponseBodyOrders.getTimeCreated();
        }
        return str;
    }

    public int get(int position) {
        return position;
    }

}
