package com.fh.shop.api.cart.vo;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private Long count = 0l;

    private String price = "0";

    private List<CartItem> list = new ArrayList<>();

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<CartItem> getList() {
        return list;
    }

    public void setList(List<CartItem> list) {
        this.list = list;
    }
}
