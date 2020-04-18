package com.example.adamos_logistic.Posts;

public class AddResponseBodyOrders {
    private String attribute_name;
    private String attribute_description;
    private String attribute_type;
    private String ERROR_ID;

    public AddResponseBodyOrders(String attribute_name, String attribute_description, String attribute_type, String ERROR_ID) {
        this.attribute_name = attribute_name;
        this.attribute_description = attribute_description;
        this.attribute_type = attribute_type;
        this.ERROR_ID = ERROR_ID;
    }

    public String getAttribute_name() {
        return attribute_name;
    }

    public String getAttribute_description() {
        return attribute_description;
    }

    public String getAttribute_type() {
        return attribute_type;
    }

    public String getERROR_ID() {
        return ERROR_ID;
    }
}
