package com.example.bookmanager.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtil {

    private static SimpleDateFormat sFormat = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * 获取当前格式化时间
     * @return
     */
    public static String getNowDate(){
        return sFormat.format(new Date());
    }
}
