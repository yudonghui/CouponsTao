package com.ydh.couponstao.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class DateFormtUtils {
    public static String YMD = "yyyy-MM-dd";

    public static String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static String YMD_HM = "yyyy-MM-dd HH:mm";
    public static String MD_HMS = "MM-dd HH:mm:ss";
    public static String MD_HM = "M-dd HH:mm";
    public static String HM = "HH:mm";
    public static String MD = "MM月dd日";

    public static int[] obtainTime(long time) {
        int[] times = new int[4];
        int i1 = (int) (time / 1000);
        int i2 = i1 / 60;
        int i3 = i2 / 60;
        int i4 = i3 / 24;
        int second = i1 % 60;
        int minute = i2 % 60;
        int hour = i3 % 24;
        int day = i4;
        times[0] = day;
        times[1] = hour;
        times[2] = minute;
        times[3] = second;
        return times;
    }

    public static String getCurrentDate() {
        return getCurrentDate(YMD);
    }

    public static String getCurrentDate(String format) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String sim = dateFormat.format(date);
        return sim;
    }

    public static String longToDate(long date) {
        if (date == 0) return "--";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String strByDate(Date date) {
        return strByDate(date, YMD);
    }

    public static String strByDate(Date date, String fomat) {
        if (date == null) return "--";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fomat);
        return simpleDateFormat.format(date);
    }

    public static String dateByDate(String year, String month, String day) {
        String yearStr = year.substring(0, year.length() - 1);
        String monthStr = month.substring(0, month.length() - 1);
        String dayStr = day.substring(0, day.length() - 1);

        if (Integer.parseInt(monthStr) < 10) {
            monthStr = "0" + monthStr;
        }
        if (Integer.parseInt(dayStr) < 10) {
            dayStr = "0" + dayStr;
        }
        return yearStr + "-" + monthStr + "-" + dayStr;
    }

    /**
     * 获取多少天 月之后
     *
     * @param type Calendar.MONTH Calendar.DATE
     */
    public static String getDateAfter(int type, int num) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(type, num);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }

    /**
     * 获取多少分钟之后
     */
    public static String getMinAfter(Date date, int minute) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HM);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }

    public static String getMinAfter(String date, int minute) {
        String[] startArray = date.split(":");
        int startHour = Integer.parseInt(startArray[0]);
        int startMinute = Integer.parseInt(startArray[1]);
        if (startMinute + minute < 60) {
            return startHour + ":" + (startMinute + minute);
        } else {
            int aa = (startMinute + minute) / 60;
            int bb = (startMinute + minute) % 60;
            return (Strings.getStrByInt(startHour + aa) + ":" + Strings.getStrByInt(bb));
        }
    }

    /**
     * 获取本周一的时间点。
     */
    public static String getWeekStart() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取本周日的时间点。
     */
    public static String getWeekEnd() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        long timeInMillis = calendar.getTimeInMillis() + 518400000;
        calendar.setTimeInMillis(timeInMillis);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取当前月份的第一天
     */
    public static String getBeginDayofMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH, 1);
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        Date firstDate = startDate.getTime();
        return simpleDateFormat.format(firstDate);

    }

    /**
     * 获取当前月份的最后一天
     */
    public static String getEndDayofMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH, startDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        startDate.set(Calendar.HOUR_OF_DAY, 23);
        startDate.set(Calendar.MINUTE, 59);
        startDate.set(Calendar.SECOND, 59);
        startDate.set(Calendar.MILLISECOND, 999);
        Date firstDate = startDate.getTime();
        return simpleDateFormat.format(firstDate);
    }

    /**
     * 获取一个月前的日期
     *
     * @param date 传入的日期
     * @return
     */
    public static String getMonthAgo(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }

    public static String getDayAfter(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }

    static String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static String forecastDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        return weekDays[i - 1];
    }

    // 将年月日转换成星期
    public static String getWeek(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date parse = format.parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse);
            int i1 = cal.get(Calendar.DAY_OF_WEEK);
            return weekDays[i1 - 1];
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String dateByLong(long time) {
        if (time == 0) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return simpleDateFormat.format(cal.getTime());
    }

    public static long getLongByDate(String string, String format) {
        if (TextUtils.isEmpty(string)) return 0;
        if (TextUtils.isEmpty(format)) format = YMD_HMS;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String showformatDate(Calendar selectCalendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String selectDate = sdf.format(selectCalendar.getTime());
        String todayDate = sdf.format(Calendar.getInstance().getTime());
        if (selectDate.equals(todayDate)) {
            selectDate = selectDate + " (今天)";
        }
        return selectDate;
    }

    public static String getStringByCalendar(Calendar selectCalendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(selectCalendar.getTime());
    }

    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    public static String timeDifference(long beforeTime) {
        if (beforeTime == 0) return "";
        long currentTime = System.currentTimeMillis();
        long difTime = (currentTime - beforeTime) / 1000;
        if (difTime <= 60) {
            return "1分钟前";
        } else if (difTime > 60 && difTime <= 3600) {
            int time = (int) (difTime / 60);
            return time + "分钟前";
        } else if (difTime > 3600 && difTime <= 86400) {
            int time = (int) (difTime / 3600);
            return time + "小时前";
        } else if (difTime > 86400 && difTime <= 2538000) {
            int time = (int) (difTime / 86400);
            return time + "天前";
        }
        if (difTime > 2538000 && difTime <= 31536000) {
            int time = (int) (difTime / 2538000);
            return time + "月前";
        } else {
            int time = (int) (difTime / 31536000);
            return time + "年前";
        }

    }

    /**
     * 截取小时和分钟
     * 传入 2019-12-15 12:30:45
     * 返回 12:30
     */
    public static String getTimeLines(String time) {
        if (TextUtils.isEmpty(time) || time.length() < 19) return "";
        return time.substring(11, 16);
    }

    /**
     * 通过给的时间，获取距离当前的时间间隔
     * 精确到分钟
     */
    public static String getTimeDuration(String time, String format) {
        if (TextUtils.isEmpty(time)) return "";
        long timeSpanByNow = (System.currentTimeMillis() - getLongByDate(time, format)) / 60000;
        if (timeSpanByNow <= 0) return "";
        return getTimeStr(timeSpanByNow);
    }

    /**
     * 通过给的时间，获取距离当前的时间间隔
     * 精确到分钟
     */
    public static String getTimeDuration(String time) {
        if (TextUtils.isEmpty(time)) return "0分钟";
        int timeSpanByNow = Integer.parseInt(time);
        if (timeSpanByNow <= 0) return "0分钟";
        return getTimeStr(timeSpanByNow);
    }

    public static String getTimeDurationFinish(String startTime, String finishTime, String format) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(finishTime)) return "";
        long timeSpanByNow = (getLongByDate(finishTime, format) - getLongByDate(startTime, format)) / 60000;
        if (timeSpanByNow <= 0) return "";
        return getTimeStr(timeSpanByNow);
    }

    private static String getTimeStr(long timeSpanByNow) {
        long minute = timeSpanByNow % 60;//分钟
        long hour = (timeSpanByNow / 60) % 24;//小时
        long day = timeSpanByNow / 1440;//天
        String timeDuration = "";
        if (day > 365) {
            timeDuration = "一年前";
        } else {
            if (day == 0) {
                if (hour == 0) {
                    if (minute == 0) {
                        timeDuration = "刚刚";
                    } else {
                        timeDuration = minute + "分钟";
                    }
                } else {
                    if (minute == 0) {
                        timeDuration = hour + "小时";
                    } else {
                        timeDuration = hour + "小时" + minute + "分钟";
                    }
                }
            } else {
                if (hour == 0) {
                    if (minute == 0) {
                        timeDuration = day + "天";
                    } else {
                        timeDuration = day + "天" + minute + "分钟";
                    }
                } else {
                    if (minute == 0) {
                        timeDuration = day + "天" + hour + "小时";
                    } else {
                        timeDuration = day + "天" + hour + "小时" + minute + "分钟";
                    }
                }
            }
        }
        return timeDuration;
    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static String getWeek(int week) {
        String weekStr = "";
        switch (week) {
            case 1:
                weekStr = "周日";
                break;
            case 2:
                weekStr = "周一";
                break;
            case 3:
                weekStr = "周二";
                break;
            case 4:
                weekStr = "周三";
                break;
            case 5:
                weekStr = "周四";
                break;
            case 6:
                weekStr = "周五";
                break;
            case 7:
                weekStr = "周六";
                break;
            default:
                break;
        }
        return weekStr;
    }
}
