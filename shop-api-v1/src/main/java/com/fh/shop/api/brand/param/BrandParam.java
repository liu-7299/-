package com.fh.shop.api.brand.param;

import com.fh.shop.api.commons.Page;

import java.io.Serializable;

public class BrandParam extends Page implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
