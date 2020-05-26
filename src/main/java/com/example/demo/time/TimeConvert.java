package com.example.demo.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class TimeConvert {

    public static String getStandardTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtils.FORMAT1, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Date date = new Date(timestamp+8*60*60*1000);
        sdf.format(date);
        return sdf.format(date);
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println(getStandardTime(time));

        Date dd = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtils.FORMAT1, Locale.getDefault());
        System.out.println(sdf.format(dd));

        HashMap hashMap;

    }

}
