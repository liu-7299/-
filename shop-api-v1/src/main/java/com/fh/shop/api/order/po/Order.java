package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {

    @TableId(type=IdType.INPUT)
    private String id;//订单id
    private Long memberId;//会员id
    private BigDecimal payPrice;//总价
    private Long commCount;//数量

    private Date createTime;//创建时间
    private Date payTime;//支付时间
    private Integer payWay;//支付方式
    private Date sendTime;//发货时间
    private String evaluateTime;//评价时间
    private String makeTime;//交易成交时间
    private Integer orderStatus;//订单状态

    private String consigneeName;//收货人姓名
    private String consigneeRegion;//收货人地区
    private String consigneePhone;//收货人手机
    private Integer expressWay;//快递方式
    private BigDecimal expressFee;//快递费
    private Integer invoice;//是否需要发票

}
