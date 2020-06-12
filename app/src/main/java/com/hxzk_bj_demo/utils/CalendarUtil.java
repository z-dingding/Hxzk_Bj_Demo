package com.hxzk_bj_demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ${赵江涛} on 2018-1-25.
 * 作用:获取当前时间
 */

public class CalendarUtil {

    private volatile static CalendarUtil mCalendarUtil;


    private CalendarUtil(){};


    public static CalendarUtil getInstance(){
        if(mCalendarUtil == null){
            synchronized (CalendarUtil.class){
                if(mCalendarUtil == null){
                    mCalendarUtil =new CalendarUtil();
                }
            }
        }
        return mCalendarUtil;
    }


    /**
     * 获取当前时间
     * 格式 2018-1-25
     * @return
     */
    public   String getTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(d);

    }

    /**
     * 时间戳(毫秒)转日期
     * @param seconds
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date(Long.parseLong(seconds));
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

}
