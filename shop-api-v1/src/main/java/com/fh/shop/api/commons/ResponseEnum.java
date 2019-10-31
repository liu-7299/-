package com.fh.shop.api.commons;

public enum
ResponseEnum {

    PHONE_IS_NULL(5000,"手机号不能为空"),
    PHONE_IS_ERROR(5001,"请输入正确的手机号"),
    SEND_ERROR(5002,"发送错误,请重试"),
    SEND_SUCCESS(5003,"发送成功,验证码有效期2分钟"),
    PARAM_IS_NULL(5004,"必填信息不能为空"),
    PASS_IS_NO_FIT(5005,"两次密码不一致"),
    CODE_IS_NULL(5006,"验证码过时或未发送验证码"),
    USER_IS_EXIET(5007,"用户已注册"),
    EMAIL_IS_EXIET(5008,"邮箱已注册"),
    PHONE_IS_EXIET(5009,"手机已注册"),
    USER_SUCCESS(5010,"注册成功"),
    EMAIL_IS_ERROR(5011,"请输入正确的邮箱名"),
    USER_IS_ERROR(5012,"请输入正确的用户名"),
    IS_CONTAINS_ERROR(5013,"不能包含空格"),
    USER_OR_PASS_IS_NULL(5014,"用户名或者密码不能为空"),
    USER_OR_PASS_IS_ERROR(5015,"用户名或者密码错误"),

    CART_IS_NULL(5016,"购物车为空"),
    COMM_IS_NO_EXIET(5016,"商品不存在"),
    COMM_IS_NO_PUT(5016,"商品未上架"),
    COMM_IS_NO_ERROR(5017,"数据异常"),
    ALL_COMM_IS_NULL(1000,"该订单所有商品库存不足"),

    TOP_LOSE(6000,"头信息丢失"),
    TOP_LACK(6001,"头信息缺少"),
    TOP_ERROR(6002,"头信息被篡改"),
    LOGIN_TIMEOUT(6003,"登录超时"),
    REQUEST_TIMEOUT(6004,"登录超时"),

    RESULT_IS__NULL(4999,"结果不存在");

    private Integer code;

    private String msg;

    private ResponseEnum(Integer code, String msg){
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
