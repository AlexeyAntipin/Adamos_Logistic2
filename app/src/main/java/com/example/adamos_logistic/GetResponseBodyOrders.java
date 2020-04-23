package com.example.adamos_logistic;

import java.util.List;

public class GetResponseBodyOrders {

    private String name;
    private int order_id;
    private String ERROR_ID;
    private List<OrderAttributes> ATTRIBUTES;

    public GetResponseBodyOrders(String name, int order_id, String ERROR_ID, List<OrderAttributes> orderAttributes) {
        this.name = name;
        this.order_id = order_id;
        this.ERROR_ID = ERROR_ID;
        this.ATTRIBUTES = orderAttributes;
    }

    public String getName() {
        return name;
    }

    public int getOrder_id() {
        return order_id;
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
