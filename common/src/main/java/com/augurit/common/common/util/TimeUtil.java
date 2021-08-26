package com.augurit.common.common.util;

import android.text.TextUtils;

import com.augurit.agmobile.common.lib.time.TimeCompare;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author 创建人 ：liangshenghong
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol.track.util
 * @createTime 创建时间 ：2017-06-20
 * @modifyBy 修改人 ：liangshenghong
 * @modifyTime 修改时间 ：2017-06-20
 * @modifyMemo 修改备注：
 */

public class TimeUtil {
    public enum AddDateType {
        YEAR, MONTH, DAY, HH, MM, SS
    }

    public static String formatSecond(int totalSecond) {
        int hour = totalSecond / 3600;
        int residueSecond = totalSecond % 3600;
        int minute = residueSecond / 60;
        int second = residueSecond % 60;
        String hourStr = String.valueOf(hour);
        if (hour < 10) {
            hourStr = "0" + hourStr;
        }
        String minuteStr = String.valueOf(minute);
        if (minute < 10) {
            minuteStr = "0" + minuteStr;
        }
        String secondStr = String.valueOf(second);
        if (second < 10) {
            secondStr = "0" + secondStr;
        }
        return hourStr + ":" + minuteStr + ":" + secondStr;
    }

    /**
     * 获取当天0时的时间戳
     *
     * @return
     */
    public static long getDayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return cal.getTimeInMillis();
    }

    public static long getTimestampRamdon() {
        long timestamp = System.currentTimeMillis();
        int random = (int) (Math.random() * 1000);
        return Long.valueOf(String.valueOf(timestamp).substring(3) + random);
    }


    public static String getStringTimeYMDS(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(date);
    }

    public static String getStringTimeMDS(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
                .format(date);
    }


    public static String getStringTimeDS(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                .format(date);
    }

    public static String getStringTimeHm(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(date);
    }

    public static Date getDateTimeYMDS(String dateString) throws ParseException {
        if (dateString == null || dateString.equals(""))
            return new Date();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .parse(dateString);
    }

    public static String getStringTimeYYMMDDSS(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
                .format(date);
    }

    public static String getStringTimeYMDSFromDate(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(date);
    }

    public static String getStringTimeYMDFromDate(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(date);
    }

    public static Date getDateTimeYYMMDDSS(String dateString)
            throws ParseException {
        if (dateString == null || dateString.equals(""))
            return new Date();
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
                .parse(dateString);
    }

    public static Date getNewDate() {
        return new Date();
    }

    public static String getStringTimeYMD(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(date);
    }

    public static Date getDateTimeYMD(String dateString) throws ParseException {
        if (dateString == null || dateString.equals(""))
            return new Date();
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(dateString);
    }

    public static String getStringTimeYYMMDD(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                .format(date);
    }

    public static Date getDateTimeYYMMDD(String dateString)
            throws ParseException {
        if (dateString == null || dateString.equals(""))
            return new Date();
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                .parse(dateString);
    }

    public static String getStringTimeYMDSChines(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.getDefault())
                .format(date);
    }

    public static Date getDateTimeYMDSChines(String dateString)
            throws ParseException {
        if (dateString == null || dateString.equals(""))
            return new Date();
        return new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.getDefault())
                .parse(dateString);
    }

    public static String getStringTimeYMDMChines(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy年MM月dd日HH时mm分", Locale.getDefault())
                .format(date);
    }

    public static String getStringTimeMdHmChines(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("MM月dd日HH时mm分", Locale.getDefault())
                .format(date);
    }

    public static Date getDateTimeYMDMChines(String dateString)
            throws ParseException {
        if (dateString == null || dateString.equals(""))
            return new Date();
        return new SimpleDateFormat("yyyy年MM月dd日HH时mm分", Locale.getDefault())
                .parse(dateString);
    }

    public static String getStringTimeHHmmChinese(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("HH时mm分", Locale.getDefault())
                .format(date);
    }

    public static String getStringTimeYMDChines(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
                .format(date);
    }

    public static String getStringTimeYMDHm(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault())
                .format(date);
    }

    public static Date getDateTimeYMDChines(String dateString)
            throws ParseException {
        if (dateString == null || dateString.equals(""))
            return new Date();
        return new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
                .parse(dateString);
    }

    public static Long getCurrentTimeMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String getTimeStamp() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return String.valueOf(ts.getTime());
    }


    /**
     * @param date1 The time that will compare with date2 shouldn't be null and it
     *              has to be the correct date format:"yyyy-MM-dd".
     * @param date2 The time that is to be compared with date1 and it has to be
     *              the correct date format:"yyyy-MM-dd".If it is null,the default
     *              value will be the current time.
     * @param stype 0: how many days; 1: how many months; 2: how many years.
     * @return:TimeCompare
     */
    public static TimeCompare compareDate(String date1, String date2, int stype)
            throws ParseException {
        if (date1 == null) {
            throw new NullPointerException(
                    "The first Date parameter cannot be null");
        }
        TimeCompare timeCompare = new TimeCompare();
        int n = 0;

        String[] u = {"天", "月", "年"};
        String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

        date2 = date2 == null ? getStringTimeYMD(getNewDate()) : date2;

        DateFormat df = new SimpleDateFormat(formatStyle, Locale.getDefault());
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(df.parse(date1));
        c2.setTime(df.parse(date2));
        // List list = new ArrayList();
        if (!c1.after(c2)) {
            timeCompare.setBigger(false);
            while (!c1.after(c2)) { // To compare Circularly , until c2 is after
                // c1, n-1 is the results
                // list.add(df.format(c1.getTime())); // Here you can put the
                // dates which are on interval in the array and print it out
                n++;
                if (stype == 1) {
                    c1.add(Calendar.MONTH, 1); // Compare month,plus one
                } else {
                    c1.add(Calendar.DATE, 1); // Compare day,plus one
                }
            }
        } else {
            timeCompare.setBigger(true);
            while (c1.after(c2) || c1.equals(c2)) { // To compare Circularly ,
                // until c1 is after c2, n-1
                // is the results
                // list.add(df.format(c1.getTime())); // Here you can put the
                // dates which are on interval in the array and print it out
                n++;
                if (stype == 1) {
                    c2.add(Calendar.MONTH, 1); // Compare month,plus one
                } else {
                    c2.add(Calendar.DATE, 1); // Compare day,plus one
                }
            }
        }
        if (stype == 2) {
            // The result for years comparation.No need to
            // minus one.
            n = n / 365;
        } else {
            n = n - 1;
        }
        // System.out.println(date1 + " -- " + date2 + " how many" + u[stype] +
        // ":"
        // + n);
        timeCompare.setDifference(n);
        return timeCompare;
    }

    public static Date getSSDate(int index) {
        Date date = getDate(new Date(), AddDateType.SS, index);
        return date;
    }

    /**
     * To get the Date before or after the current time that is compared with
     * different type.
     *
     * @param date
     * @param dateType
     * @param index
     * @return
     */
    public static Date getDate(Date date, AddDateType dateType, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (AddDateType.YEAR.equals(dateType)) {
            calendar.add(Calendar.YEAR, index);
            return calendar.getTime();
        }
        if (AddDateType.MONTH.equals(dateType)) {
            calendar.add(Calendar.MONTH, index);
            return calendar.getTime();
        }
        if (AddDateType.DAY.equals(dateType)) {
            calendar.add(Calendar.DAY_OF_MONTH, index);
            return calendar.getTime();
        }
        if (AddDateType.HH.equals(dateType)) {
            calendar.add(Calendar.HOUR_OF_DAY, index);
            return calendar.getTime();
        }
        if (AddDateType.MM.equals(dateType)) {
            calendar.add(Calendar.MINUTE, index);
            return calendar.getTime();
        }
        if (AddDateType.SS.equals(dateType)) {
            calendar.add(Calendar.SECOND, index);
            return calendar.getTime();
        }
        return date;
    }

    /**
     * To convert the milliseconds to special format,like 03:10.
     *
     * @param millisencond
     * @return
     */
    public static String convertMilliSecondToMinute(int millisencond) {
        int oneMinute = 1000 * 60;
        int minutes = millisencond / oneMinute;
        int sencond = (millisencond - minutes * oneMinute) / 1000;
        return getNum(minutes) + ":" + getNum(sencond);
    }

    /**
     * To convert the seconds to special format,like 03:10.
     *
     * @param seconds
     * @return
     */
    public static String convertSecondToMinute(int seconds) {
        int oneMinute = 60;
        int minutes = seconds / oneMinute;
        int mSecond = seconds - minutes * oneMinute;
        return getNum(minutes) + ":" + getNum(mSecond);
    }

    public static String getNum(int num) {
        if (num >= 10) {
            return "" + num;
        } else {
            return "0" + num;
        }
    }

    /**
     * Get the string format of current time by custom format,such as
     * "MM-dd HH:mm"
     *
     * @param customFormat
     * @return
     */
    public static String getCustomNowDateString(String customFormat) {
        Date nowDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat(customFormat);
        String nowDateString = dateFormat.format(nowDate);
        return nowDateString;
    }


    public static Date getDateTime(String dateString, String formateOfDate) throws ParseException {
        if (TextUtils.isEmpty(dateString))
            return new Date();
        return new SimpleDateFormat(formateOfDate, Locale.getDefault())
                .parse(dateString);
    }

    /**
     * 获取两个时间的时间查 如1天2小时30分钟
     */
    public static String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60L;
        long nh = 1000 * 60 * 60L;
        long nm = 1000 * 60L;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
       /* // 计算差多少天
        long day = diff / nd;*/
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return hour + "小时" + min + "分钟";
    }

    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static String dateToStr(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
}
