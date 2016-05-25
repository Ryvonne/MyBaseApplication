package com.base.remiany.mybaseapplication.utils;

import android.text.TextUtils;

/**
 * Created by lxl on 2015/10/28.
 */
public class StringUtil {
    /**
     * 判断字符串是否为有效字符串
     *
     * @param str
     * @return
     */
    public static boolean isVaild(String str) {
        if (TextUtils.isEmpty(str) || "null".equals(str)) {
            return false;
        }
        return true;
    }

    /**
     * 判断给定的文本是否是整数
     *
     * @param numberString
     * @return
     */
    public static boolean isNumber(String numberString) {
        if (TextUtils.isEmpty(numberString) || "null".equals(numberString)) {
            return false;
        }

        return numberString.matches("^[0-9]*$");
    }

    /**
     * 判断给定文本是否是小数
     *
     * @param numberString
     * @return
     */
    public static boolean isDecimal(String numberString) {
        if (isNumber(numberString)) {
            return true;
        }
        return numberString.matches("^\\d+\\.\\d+$");
    }

    /**
     * 判断指定的字符串是否是合法的电话号码
     *
     * @param numberString
     * @return
     */
    public static boolean isPhoneNumber(String numberString) {
        boolean isNumber = false;
        if (!numberString.equals("")) {
            if (numberString.length() == 11
                    && (isNumber(numberString))
                    && (numberString.startsWith("13")
                    || numberString.startsWith("18")
                    || numberString.startsWith("15") || numberString
                    .startsWith("14"))) {
                isNumber = true;
            }
        }

        return isNumber;
    }

    /**
     * 输入字符串，返回指定长度的字符串，超出的截取，不足位的后面补零
     *
     * @param str
     * @param slen
     * @return
     */
    public static String StringLengthHelper(String str, int slen) {
        if (str.length() < slen) {
            int leng = slen - str.length();
            for (int i = 0; i < leng; i++) {
                str += "0";
            }
        } else if (str.length() > slen) {
            str = str.substring(0, slen);
        }
        return str;
    }
}
