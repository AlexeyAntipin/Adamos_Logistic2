package com.example.adamos_logistic.Posts;

public class OrderAttributes {

    private int attribute_id;
    private int attribute_order_id;
    private int attribute_value_id;
    private int order_id;
    private String value;
    private String name;

    public OrderAttributes(int attribute_id, int attribute_order_id,
                           int attribute_value_id, int order_id, String value, String name) {
        this.attribute_id = attribute_id;
        this.attribute_order_id = attribute_order_id;
        this.attribute_value_id = attribute_value_id;
        this.order_id = order_id;
        this.value = value;
        this.name = name;
    }

    public int getAttribute_id() {
        return attribute_id;
    }

    public int getAttribute_order_id() {
        return attribute_order_id;
    }

    public int getAttribute_value_id() {
        return attribute_value_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
