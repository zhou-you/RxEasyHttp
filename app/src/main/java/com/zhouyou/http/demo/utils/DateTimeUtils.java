package com.zhouyou.http.demo.utils;

import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <b>类名称：</b> DateTimeUtils <br/>
 * <b>类描述：</b> 日期时间相关的工具类<br/>
 * <b>创建人：</b> 林肯 <br/>
 * <b>修改人：</b> 编辑人 <br/>
 * <b>修改时间：</b> 2015年07月29日 下午4:16 <br/>
 * <b>修改备注：</b> <br/>
 *
 * @version 1.0.0 <br/>
 */
public class DateTimeUtils {
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    private static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.SSS
     */
    private static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    private DateTimeUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获得默认的 date pattern
     */
    private static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 根据预设格式返回当前日期
     *
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    private static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    private static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return
     */
    private static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    private static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL, Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户给的时间戳获取预设格式的时间
     *
     * @param date    时间戳
     * @param pattern 预设时间格式
     * @return
     */
    public static String getDate(String date, String pattern) {
        Date dates = new Date();
        dates.setTime(Long.parseLong(date));
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(dates);
    }

    /**
     * 格式化时间 判断一个日期 是否为 今天、昨天
     *
     * @param time
     * @return
     */
    public static String formatDateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {

            return "昨天 " + time.split(" ")[1];
        } else {
//            int index = time.indexOf("-")+1;
//            return time.substring(index, time.length());
            int index = time.indexOf(" ");
            return time.substring(0, index);
        }
    }

    /**
     * 将UTC-0时区时间字符串转换成用户时区时间的描述.
     *
     * @param strUtcTime UTC-0时区的时间
     * @param strInFmt   时间的输入格式
     * @param strOutFmt  时间的输出格式，若为null则输出格式与输入格式相同
     * @return 用户时区的时间描述.
     * @throws ParseException 时间转换异常
     */
    public static String getUserZoneString(final String strUtcTime,
                                           final String strInFmt, final String strOutFmt)
            throws ParseException {
        if (StringUtils.isNull(strUtcTime)) {
            throw new NullPointerException("参数strDate不能为空");
        } else if (StringUtils.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        }
        long lUserMillis = getUserZoneMillis(strUtcTime, strInFmt);
        String strFmt = strInFmt;
        if (!StringUtils.isNull(strOutFmt)) {
            strFmt = strOutFmt;
        }
        return format(lUserMillis, strFmt);
    }

    /**
     * 格式化时间.
     *
     * @param lMillis  时间参数
     * @param strInFmt 时间格式
     * @return 对应的时间字符串
     */
    public static String format(final long lMillis, final String strInFmt) {
        if (StringUtils.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        }
        return (String) DateFormat.format(strInFmt, lMillis);
    }

    /**
     * 将UTC-0时区时间字符串转换成用户时区时间距离1970-01-01的毫秒数.
     *
     * @param strUtcTime UTC-0时区的时间字符串
     * @param strInFmt   时间格式
     * @return 用户时区时间距离1970-01-01的毫秒数.
     * @throws ParseException 时间转换异常
     */
    @SuppressWarnings("deprecation")
    public static long getUserZoneMillis(final String strUtcTime,
                                         final String strInFmt) throws ParseException {
        if (StringUtils.isNull(strUtcTime)) {
            throw new NullPointerException("参数strUtcTime不能为空");
        } else if (StringUtils.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        }
        long lUtcMillis = parseMillis(strUtcTime, strInFmt);
        Time time = new Time();
        time.setToNow();
        long lOffset = time.gmtoff * DateUtils.SECOND_IN_MILLIS;
        long lUserZoneMillis = lUtcMillis + lOffset;
        return lUserZoneMillis;
    }

    /**
     * 转换时间格式，将字符串转换为距离1970-01-01的毫秒数.
     *
     * @param strDate  指定时间的字符串
     * @param strInFmt 时间字符串的格式
     * @return 指定时间字符串距离1970-01-01的毫秒数
     * @throws ParseException 时间转换异常
     */
    public static long parseMillis(final String strDate, final String strInFmt)
            throws ParseException {
        if (StringUtils.isNull(strDate)) {
            throw new NullPointerException("参数strDate不能为空");
        } else if (StringUtils.isNull(strInFmt)) {
            throw new NullPointerException("参数strInFmt不能为空");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(strInFmt,
                Locale.getDefault());
        Date date = sdf.parse(strDate);
        return date.getTime();
    }

    public static String utc2BeiJingTime(String message) {
        String beiJingTime = message;
        if (message.contains("#")) {
            String[] loginInfo = message.split("#");
            if (loginInfo != null && loginInfo.length >= 3) {
                try {
                    String utcTime = loginInfo[1];
                    beiJingTime = DateTimeUtils.getUserZoneString(utcTime, "HH:mm", null);
                    String repaceTimeStr = "#"+utcTime+"#";
                    beiJingTime = message.replace(repaceTimeStr,beiJingTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return beiJingTime;
    }

}
