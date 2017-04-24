package com.backend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 *
 * @author Skynet
 * @date 2017年04月24日 13:09
 */
public class DateUtils {

    /** 时间格式(yyyy-MM-dd) */
    private final static String DATE_PATTERN = "yyyy-MM-dd";

    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String formate(Date date){
        return formate(date, DATE_PATTERN);
    }

    public static String formate(Date date, String pattern){
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

}
