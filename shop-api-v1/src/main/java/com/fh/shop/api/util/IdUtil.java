package com.fh.shop.api.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdUtil {

    //雪花id
    public static String getId(){
        DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyyMMddHHmm");//yyyyMMddHHmmssSSS
        return LocalDateTime.now().format(time) + IdWorker.getId();
    }

}
