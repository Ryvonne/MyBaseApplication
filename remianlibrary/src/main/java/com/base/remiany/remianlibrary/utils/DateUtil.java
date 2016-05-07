package com.base.remiany.remianlibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    static Calendar mCalendar = Calendar.getInstance();
    static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 返回当前时间的默认格式（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String getStringDate() {
        return mFormat.format(new Date());
    }

    /**
     * 返回当前时间的指定格式
     * @param format
     * @return
     */
    public static String getDate(String format) {
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(new Date());
    }

    /**
     * 获取当前时间
     * @return
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 将缺少补0的日期转换为正确格式
     *
     * @param date
     * @return
     */
    public static String tranFormatDate(String date) {
        String[] dates = date.split("-");

        String newdate = dates[0] + "-";
        int month = Integer.valueOf(dates[1]);

        if (month < 10) {
            newdate += "0";
        }
        newdate += month + "-";

        int day = Integer.valueOf(dates[2]);
        if (day < 10) {
            newdate += "0";
        }
        newdate += day;

        return newdate;
    }

    /**
     * 输入年月日，返回日期格式
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getFormatDate(int year, int month, int day) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month - 1);
        mCalendar.set(Calendar.DAY_OF_YEAR, day);
        String date = mFormat.format(mCalendar.getTime());
        return date;
    }

}
