package com.example.adamos_logistic.Posts;

import java.util.List;

public class GetResponseBodyOrdersList {
    private List<GetResponseBodyOrders> list;

    public GetResponseBodyOrdersList(List<GetResponseBodyOrders> list) {
        this.list = list;
    }

    public List<GetResponseBodyOrders> getList() {
        return list;
    }
}
