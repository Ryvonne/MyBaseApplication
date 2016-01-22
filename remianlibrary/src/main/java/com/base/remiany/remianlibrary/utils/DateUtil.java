package com.base.remiany.remianlibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    static Calendar mCalendar = Calendar.getInstance();
    static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDate() {
        return mFormat.format(new Date());
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
