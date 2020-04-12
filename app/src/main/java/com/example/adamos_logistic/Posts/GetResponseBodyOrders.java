package com.example.adamos_logistic.Posts;

import java.util.List;

public class GetResponseBodyOrders {

    private String name;
    private String ERROR_ID;
    private List<OrderAttributes> orderAttributes;

    public GetResponseBodyOrders(String name, String ERROR_ID, List<OrderAttributes> orderAttributes) {
        this.name = name;
        this.ERROR_ID = ERROR_ID;
        this.orderAttributes = orderAttributes;
    }

    public String getName() {
        return name;
    }

    public String getERROR_ID() {
        return ERROR_ID;
    }
}
