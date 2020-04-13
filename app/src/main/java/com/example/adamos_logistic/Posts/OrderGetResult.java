package com.example.adamos_logistic.Posts;

import java.util.List;

public class OrderGetResult {
    private List<GetResponseBodyOrders> ORDERS;
    private List<OrderAttributes> ATTRIBUTES;

    public OrderGetResult(List<GetResponseBodyOrders> ORDERS, List<OrderAttributes> ATTRIBUTES) {
        this.ORDERS = ORDERS;
        this.ATTRIBUTES = ATTRIBUTES;
    }

    public List<GetResponseBodyOrders> getList() {
        return ORDERS;
    }

    public String getAttributeID() {
        String str = "";
        for (OrderAttributes orderAttributes1 : ATTRIBUTES) {
            if (orderAttributes1.getName().equals("attribute_id"))
                str = String.valueOf(orderAttributes1.getAttribute_id());
        }
        return str;
    }

    public String getOrderStatus() {
        String str = "";
        for (OrderAttributes orderAttributes1 : ATTRIBUTES) {
            if (orderAttributes1.getName().equals("order_status") &&
                )
                str = String.valueOf(orderAttributes1.getAttribute_value_id());
        }
        return str;
    }

    public String getTimeCreated() {
        String str = "";
        for (OrderAttributes orderAttributes1 : ATTRIBUTES) {
            if (orderAttributes1.getName().equals("time_created"))
                str = orderAttributes1.getValue();
        }
        return str;
    }
}
