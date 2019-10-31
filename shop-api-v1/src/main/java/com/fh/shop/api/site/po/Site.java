package com.fh.shop.api.site.po;

import lombok.Data;

@Data
public class Site {

    private Long id;

    private Long memberId;

    private String name;

    private String phone;

    private Integer r1;
    private Integer r2;
    private Integer r3;
    private String info;

    private Integer status;

}
