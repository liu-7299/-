package com.fh.admin.param;

import com.fh.admin.commons.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CommodityParam extends Page implements Serializable {

    private String name;

    private BigDecimal priceMin;

    private BigDecimal priceMax;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTimeMin;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTimeMax;

    private Integer cate1;
    private Integer cate2;
    private Integer cate3;

    private Integer[] fl_sel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(BigDecimal priceMin) {
        this.priceMin = priceMin;
    }

    public BigDecimal getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(BigDecimal priceMax) {
        this.priceMax = priceMax;
    }

    public Date getCreateTimeMin() {
        return createTimeMin;
    }

    public void setCreateTimeMin(Date createTimeMin) {
        this.createTimeMin = createTimeMin;
    }

    public Date getCreateTimeMax() {
        return createTimeMax;
    }

    public void setCreateTimeMax(Date createTimeMax) {
        this.createTimeMax = createTimeMax;
    }

    public Integer getCate1() {
        return cate1;
    }

    public void setCate1(Integer cate1) {
        this.cate1 = cate1;
    }

    public Integer getCate2() {
        return cate2;
    }

    public void setCate2(Integer cate2) {
        this.cate2 = cate2;
    }

    public Integer getCate3() {
        return cate3;
    }

    public void setCate3(Integer cate3) {
        this.cate3 = cate3;
    }

    public Integer[] getFl_sel() {
        return fl_sel;
    }

    public void setFl_sel(Integer[] fl_sel) {
        this.fl_sel = fl_sel;
        Integer i = fl_sel.length;
        Integer[] arr = this.fl_sel;
        if(i == 1) this.cate1 = arr[0];
        if(i == 2) {
            this.cate1 = arr[0];
            this.cate2 = arr[1];
        }
        if(i == 3) {
            this.cate1 = arr[0];
            this.cate2 = arr[1];
            this.cate3 = arr[2];
        }
    }
}
