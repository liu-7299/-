package com.fh.admin.vo.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserVo implements Serializable {

    private Long id;

    private String realName;

    private String userName;

    private Integer sex;

    private Integer age;

    private String phone;

    private String email;

    private Double pay;

    private String roleName;

    //@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8",locale = "zh")// HH:mm:ss
    private String entryTime;

    private String imgPath;

    private List<Integer> list = new ArrayList<Integer>();

    private boolean status;

    private Integer region1;
    private Integer region2;
    private Integer region3;

    private String regionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
