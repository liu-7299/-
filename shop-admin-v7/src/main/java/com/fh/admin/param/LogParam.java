package com.fh.admin.param;

import com.fh.admin.commons.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LogParam extends Page implements Serializable {

    private String userName;

    private String realName;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")// HH:mm:ss
    private Date createTimeMin;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTimeMax;

    private Integer status;

    private String info;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
