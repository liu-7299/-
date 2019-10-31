package com.fh.shop.api.sms.controller;

import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.sms.biz.ISMSService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("sms")
@CrossOrigin("*")
public class SMSController {

    @Resource(name="smsService")
    private ISMSService smsService;

    @GetMapping("/{phone}")
    public ServerResponse phoneCode(@PathVariable String phone) {
        return smsService.phoneCode(phone);
    }

}
