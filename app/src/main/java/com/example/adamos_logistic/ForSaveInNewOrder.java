package com.example.adamos_logistic;

public class ForSaveInNewOrder {

    private int position;
    private String attribute_name;

    public ForSaveInNewOrder(int position, String attribute_name) {
        this.position = position;
        this.attribute_name = attribute_name;
    }

    public int getPosition() {
        return position;
    }

    public String getAttribute_name() {
        return attribute_name;
    }
}
