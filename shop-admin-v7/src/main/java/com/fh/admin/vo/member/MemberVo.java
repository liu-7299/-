package com.fh.admin.vo.member;

public class MemberVo {

    private Long id;

    private String userName;

    private String realName;

    private String phone;

    private String salt;

    private String email;

    private String birthday;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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
