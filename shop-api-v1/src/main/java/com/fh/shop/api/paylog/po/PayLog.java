package com.fh.shop.api.paylog.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_payLog")
public class PayLog {

    @TableId(value = "outPayId",type = IdType.INPUT)
    private String outPayId;

    private Long memberId;

    private String orderId;

    private String makeId;

    private Date createTime;

    private Date payTime;

    private BigDecimal payMoney;

    private Integer payWay;

    private Integer payStatus;

}
