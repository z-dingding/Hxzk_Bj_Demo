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


}
