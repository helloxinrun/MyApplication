package com.ixinrun.base.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 描述：时间处理工具类。
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public final class DateUtil {
    //UTC标准格式
    public static final String FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss";
    //UTC当天开始
    public static final String FORMAT_UTC_DAY_START = "yyyy-MM-dd'T'00:00:00";
    //UTC当天结束
    public static final String FORMAT_UTC_DAY_END = "yyyy-MM-dd'T'23:59:59";

    //前端展示的格式
    // 全格式，保留毫秒
    public static final String FORMAT_YMD_HMSS = "yyyy-MM-dd HH:mm:ss.SSS";
    // 标准格式
    public static final String FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    // 通用格式
    public static final String FORMAT_YMD_HM = "yyyy-MM-dd HH:mm";
    // 整点格式
    public static final String FORMAT_YMD_HM_INT = "yyyy-MM-dd HH:00";
    // 年月日
    public static final String FORMAT_YMD = "yyyy-MM-dd";
    // 仅保留当日时间，不包含日期
    public static final String FORMAT_HMSS = "HH:mm:ss.SSS";
    // 仅保留当日时间的时、分
    public static final String FORMAT_HM = "HH:mm";

    private DateUtil() {
        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    /**
     * 后端返回的字符串时间格式化
     *
     * @param utcStr utc格式时间串
     * @param format 目标格式
     * @return
     */
    public static String format(String utcStr, String format) {
        return format(utcStr, FORMAT_UTC, format);
    }

    /**
     * 时间字符串由第一种格式转换成第二种格式
     *
     * @param dateStr 源时间串
     * @param fromFormat 源格式
     * @param toFormat 目标格式
     * @return
     */
    public static String format(String dateStr, String fromFormat, String toFormat) {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fromFormat, Locale.CHINA);
            Date date = sdf.parse(dateStr);
            sdf = new SimpleDateFormat(toFormat, Locale.CHINA);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间格式化
     *
     * @param date 日期
     * @param format 目标格式
     * @return
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 当前日期格式化
     *
     * @param format 目标格式
     * @return
     */
    public static String getCurrent(String format) {
        return format(new Date(), format);
    }

    /**
     * 字符串时间反转成Date
     *
     * @param date 源时间串
     * @param format 源时间串对应的格式
     * @return
     */
    public static Date parse(String date, String format) {
        return parse(date, new SimpleDateFormat(format, Locale.CHINA));
    }

    /**
     * 后端返回的字符串时间反转成Date
     *
     * @param utcStr utc时间串
     * @return
     */
    public static Date parse(String utcStr) {
        return parse(utcStr, new SimpleDateFormat(FORMAT_UTC, Locale.CHINA));
    }

    /**
     * 字符串时间反转成Date
     *
     * @param date
     * @param sdf
     * @return
     */
    public static Date parse(String date, DateFormat sdf) {
        if (TextUtils.isEmpty(date) || sdf == null) {
            return null;
        }
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
