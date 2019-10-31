package com.fh.admin.commons;

import java.io.Serializable;

public class ServerResponse implements Serializable {

    private Integer code;

    private String msg;

    private Object data;

    private ServerResponse(){

    }
    private ServerResponse(Integer code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ServerResponse success(){
        return new ServerResponse(200,"ok",null);
    }
    public static ServerResponse success(Object data){
        return new ServerResponse(200,"ok",data);
    }
    public static ServerResponse error(){
        return new ServerResponse(-1,"请求失败",null);
    }
    public static ServerResponse error(ResponseEnum renum){
        return new ServerResponse(renum.getCode(),renum.getMsg(),null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
