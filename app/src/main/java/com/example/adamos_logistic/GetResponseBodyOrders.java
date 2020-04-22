package com.example.adamos_logistic;

import java.util.List;

public class GetResponseBodyOrders {

    private String name;
    private String ERROR_ID;
    private List<OrderAttributes> ATTRIBUTES;

    public GetResponseBodyOrders(String name, String ERROR_ID, List<OrderAttributes> orderAttributes) {
        this.name = name;
        this.ERROR_ID = ERROR_ID;
        this.ATTRIBUTES = orderAttributes;
    }

    public String getName() {
        return name;
    }

    public String getERROR_ID() {
        return ERROR_ID;
    }

    public String getOrderStatus() {
        String str = "";
        for (OrderAttributes orderAttributes1 : ATTRIBUTES) {
            if (orderAttributes1.getName().equals("order_status")
            )
                if (orderAttributes1.getType() == 20) str = orderAttributes1.getDescription();
                else str = orderAttributes1.getValue();
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

    public List<OrderAttributes> getATTRIBUTES() {
        return ATTRIBUTES;
    }


}
