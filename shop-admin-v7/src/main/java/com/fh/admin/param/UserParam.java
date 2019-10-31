package com.fh.admin.param;

import com.fh.admin.commons.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class UserParam extends Page implements Serializable {

    private String realName;

    private String userName;

    private Long ageMin;
    private Long ageMax;

    private Double payMin;
    private Double payMax;

    @DateTimeFormat(pattern="yyyy-MM-dd")// HH:mm:ss
    private Date entryTimeMin;
    @DateTimeFormat(pattern="yyyy-MM-dd")// HH:mm:ss
    private Date entryTimeMax;

    private String selectRole;//导出角色查询条件字段
    private String[] checkboxStr = {};//角色条件查询需要字段
    private Integer roleLength;//角色条件查询需要字段

    private Integer region1;//三个地区条件查询需要字段
    private Integer region2;
    private Integer region3;
    private Integer[] region_sel;//导出地区查询条件字段 前台三个下拉框name一致

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Long ageMin) {
        this.ageMin = ageMin;
    }

    public Long getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Long ageMax) {
        this.ageMax = ageMax;
    }

    public Double getPayMin() {
        return payMin;
    }

    public void setPayMin(Double payMin) {
        this.payMin = payMin;
    }

    public Double getPayMax() {
        return payMax;
    }

    public void setPayMax(Double payMax) {
        this.payMax = payMax;
    }

    public Date getEntryTimeMin() {
        return entryTimeMin;
    }

    public void setEntryTimeMin(Date entryTimeMin) {
        this.entryTimeMin = entryTimeMin;
    }

    public Date getEntryTimeMax() {
        return entryTimeMax;
    }

    public void setEntryTimeMax(Date entryTimeMax) {
        this.entryTimeMax = entryTimeMax;
    }

    public String getSelectRole() {
        return selectRole;
    }

    public void setSelectRole(String selectRole) {
        this.selectRole = selectRole;
        this.checkboxStr = this.selectRole.split(",");
    }

    public String[] getCheckboxStr() {
        return checkboxStr;
    }

    public void setCheckboxStr(String[] checkboxStr) {
        this.checkboxStr = checkboxStr;
        this.roleLength = this.checkboxStr.length;
    }

    public Integer getRoleLength() {
        return roleLength;
    }

    public void setRoleLength(Integer roleLength) {
        this.roleLength = roleLength;
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
        Integer[] arr = region_sel;
        Integer i = arr.length;
        if(i == 1) this.region1 = region_sel[0];
        if(i == 2){
            this.region1 = region_sel[0];
            this.region2 = region_sel[1];
        }
        if(i == 3){
            this.region1 = region_sel[0];
            this.region2 = region_sel[1];
            this.region3 = region_sel[2];
        }
    }
}
