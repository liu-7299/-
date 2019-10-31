package com.fh.admin.param;

import com.fh.admin.commons.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MemberParam extends Page {

    private String userName;

    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdayMin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdayMax;

    private Integer region1;//三个地区条件查询需要字段
    private Integer region2;
    private Integer region3;
    private Integer[] region_sel;//导出地区查询条件字段 前台三个下拉框name一致

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthdayMin() {
        return birthdayMin;
    }

    public void setBirthdayMin(Date birthdayMin) {
        this.birthdayMin = birthdayMin;
    }

    public Date getBirthdayMax() {
        return birthdayMax;
    }

    public void setBirthdayMax(Date birthdayMax) {
        this.birthdayMax = birthdayMax;
    }

    public Integer getRegion1() {
        return region1;
    }

    public void setRegion1(Integer region1) {
        this.region1 = region1;
    }

    public Integer getRegion2() {
        return region2;
    }

    public void setRegion2(Integer region2) {
        this.region2 = region2;
    }

    public Integer getRegion3() {
        return region3;
    }

    public void setRegion3(Integer region3) {
        this.region3 = region3;
    }

    public Integer[] getRegion_sel() {
        return region_sel;
    }

    public void setRegion_sel(Integer[] region_sel) {
        this.region_sel = region_sel;
    }
}
