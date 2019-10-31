package com.fh.shop.api.cart.vo;

public class CartItem {

    private Long id;

    private String name;

    private String imgPath;

    private String price;

    private Long count = 0l;

    private String zprice = "0";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getZprice() {
        return zprice;
    }

    public void setZprice(String zprice) {
        this.zprice = zprice;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
