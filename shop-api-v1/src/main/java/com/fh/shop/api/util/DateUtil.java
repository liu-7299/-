package com.fh.shop.api.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil implements Serializable {

    public final static String STRING_Y_M_D = "yyyy-MM-dd";

    public final static String STRING_H_M_S = "yyyy-MM-dd HH:mm:ss";

    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String dateToString(Date time,String str){
        SimpleDateFormat sim = new SimpleDateFormat(str);
        if(time == null){
            return "";
        }
        return sim.format(time);
    }

}
