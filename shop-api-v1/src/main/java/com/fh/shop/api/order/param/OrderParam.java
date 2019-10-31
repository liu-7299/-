package com.fh.shop.api.order.param;

import lombok.Data;

@Data
public class OrderParam {

    private Integer payWay;//支付方式

    private Long siteId;//地址信息id

    private Integer invoice;//发票

}
