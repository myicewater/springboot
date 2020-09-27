package com.jaxon.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前时间字符串yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime(){


        return "["+sf.format(new Date())+"] ";
    }
}
