package com.fh.admin.commons;

public enum
ResponseEnum {

    LOGION_ERROR_USER_NULL(1000,"用户不存在"),
    LOGION_ERROR_PASS_NULL(1001,"用户、密码和验证码不能为空"),
    LOGION_ERROR_NAPW_XXXX(1002,"用户名或密码错误"),
    LOGION_ERROR_USER_SYS(1003,"用户锁定"),
    DELETE_ERROR(1004,"该数据锁定(被占用)，请先删除已使用的"),
    UPDATE_PASS_ISNULL(1005,"信息不能为空"),
    UPDATE_PASS_ISNO(1006,"两次密码不一致"),
    UPDATE_PASS_ISNOOLD(1007,"与旧密码不一致"),
    RESET_PASS_ISNO(1008,"邮箱地址错误"),
    IMGCODE_IS_ERROR(1009,"验证码不一致")
    ;

    private Integer code;

    private String msg;

    private ResponseEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
