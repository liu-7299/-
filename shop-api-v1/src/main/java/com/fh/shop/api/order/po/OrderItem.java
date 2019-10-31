package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_orderItem")
public class OrderItem {

    private String orderId;//订单id

    private Long itemId;//商品id

    private Long memberId;//会员id

    private BigDecimal price;//商品单价

    private Long count;//商品数量

    private String itemName;//商品名

    private BigDecimal allPrice;//总价

}
