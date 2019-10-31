package com.fh.admin.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private Long id;

    private String realName;

    private String userName;

    private String password;

    private Integer sex;

    private Integer age;

    private String phone;

    private String email;

    private Double pay;

    private String imgPath;

    @DateTimeFormat(pattern="yyyy-MM-dd")// HH:mm:ss
    private Date entryTime;

    private Integer loginCount;

    private Date topLoginTime;

    private int errorCount;

    private Date errorTime;

    private String salt;

    private Integer region1;
    private Integer region2;
    private Integer region3;

    private String regionName;
    //验证码
    private String code;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getTopLoginTime() {
        return topLoginTime;
    }

    public void setTopLoginTime(Date topLoginTime) {
        this.topLoginTime = topLoginTime;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
