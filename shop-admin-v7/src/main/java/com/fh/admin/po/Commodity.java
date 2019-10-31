package com.fh.admin.po;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Commodity implements Serializable {

    private Long id;

    private String name;

    private BigDecimal price;

    private String imgPath;

    private Long stock;

    private Integer status;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Long brandId;
//排除指定列
    @TableField(exist = false)
    private String brandName;

    private Integer cate1;
    private Integer cate2;
    private Integer cate3;

    private String flName;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getFlName() {
        return flName;
    }

    public void setFlName(String flName) {
        this.flName = flName;
    }
}
