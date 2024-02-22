package com.yolo.hw.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class DateUtils {
    public static String YYYY = "yyyy";
    public static String YYYY_CN = "yyyy年";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY_MM_CN = "yyyy年MM月";
    public static String YYYY_MM_DD_CN = "yyyy年MM月dd日";
    public static String YYYYMMDD = "yyyyMMdd";
    public static String YYYYMM = "yyyyMM";
    public static String MM_DD = "MM-dd";
    public static String MM_DD_CN = "MM月dd日";
    public static String MM__DD = "MM/dd";
    public static String MM = "MM";
    public static String DD = "dd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String HH_MM_SS = "HH:mm:ss";
    public static String HH_MM_SS_CN = "HH时mm分ss秒";
    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static String MM_DD_HH_MM_SS_COMMA = "MM-dd , HH:mm:ss";
    public static String MM_DD_HH_MM = "MM/dd HH:mm";
    public static String HH_MM_00 = "HH:mm:00";
    public static String HH_MM = "HH:mm";
    private static String[] XINQIOFDAYS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private static String[] ZHOUOFDAYS = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    public static double GB_D = 1024 * 1024 * 1024.0;
    public static double MB_D = 1024 * 1024.0;
    public static double KB_D = 1024.0;

    public static Integer getRandom(Integer x, Integer y) {
        Integer rtnInt = 0;
        if (x == null || y == null || x < 0 || y < 0) {
            return rtnInt;
        } else {
            int max = Math.max(x, y);
            int min = Math.min(x, y);
            int mid = max - min;//求差
            //产生随机数
            rtnInt = (int) (Math.random() * (mid + 1)) + min;
        }
        return rtnInt;
    }

    public static boolean validTime(String timeStr) {
        String pattern = "[0-2]\\d:[0-5]\\d:[0-5]\\d";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(timeStr);
        return m.matches();
    }

    public static boolean validDateMills(Long mills) {
        String pattern = "\\d{13}";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(String.valueOf(mills));
        return m.matches();
    }

    public static String getSpaceSize(Long byteLong) {
        String strRtn = "0GB";
        if(byteLong != null && byteLong > 0) {
            if(byteLong / GB_D > 1) {
                strRtn = formattedDecimalOne(Double.valueOf(byteLong / GB_D)) + "GB";
            } else if (byteLong / MB_D > 1) {
                strRtn = formattedDecimalOne(Double.valueOf(byteLong / MB_D)) + "MB";
            } else {
                strRtn = formattedDecimalOne(Double.valueOf(byteLong / KB_D)) + "KB";
            }
        }
        return strRtn;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @return
     */
    public static int differentDaysByMillisecond(Date minDate, Date maxDate) {
        double days = Math.ceil((maxDate.getTime() - minDate.getTime()) / (1000 * 3600 * 24.0));
        return Double.valueOf(days).intValue();
    }

    public static int differentMinsByMillisecond(Date minDate, Date maxDate) {
        double days = Math.ceil((maxDate.getTime() - minDate.getTime()) / (60 * 1000.0));
        return Double.valueOf(days).intValue();
    }

    /**
     * 获取昨天的最开始时间 时分秒为00:00:00
     *
     * @return Date
     */
    public static Date getToDayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDayByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    public static Date getDayEndByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getNowDayEndByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    public static Date getMinuteByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTimeByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getYearStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.MONTH) + 1;
    }
    public static int getNowDay() {
        String minDayStr = ZMDateTimeUtil.formMatter28800INT(new Date(), 28800);
        return Integer.parseInt(minDayStr);
    }

    public static int getTimeDay(Date timeDate) {
        String minDayStr = ZMDateTimeUtil.formMatter28800INT(timeDate, 28800);
        return Integer.parseInt(minDayStr);
    }

    public static Date getYearStart(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getBeforeOrAfterDay(long day) {
        Calendar calendar = Calendar.getInstance();
        Long mils = System.currentTimeMillis() + (24 * 3600 * 1000 * day);
        calendar.setTime(new Date(mils));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 12:50:00
     *
     * @param timeStr
     * @return
     */
    public static Date getTimeByStr(String timeStr) {
        String[] tempArrs = timeStr.split(" ");
        timeStr = tempArrs[1];
        String[] times = timeStr.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0].trim()));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1].trim()));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getFenceTimeByStr(String timeStr) {
        String[] times = timeStr.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0].trim()));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1].trim()));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取昨天的最开始时间 时分秒为00:00:00
     *
     * @return Date
     */
    public static Date getYesterDayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取昨天的结束时间 时分秒为23:59:59
     *
     * @return Date
     */
    public static Date getYesterDayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String formattedDoubleToStr(double decimal) {
        DecimalFormat df = new DecimalFormat("######0.0");
        String result = df.format(decimal);
        return result;
    }

    public static double formattedDecimal(double decimal) {
        DecimalFormat df = new DecimalFormat("######0.00");
        String result = df.format(decimal);
        return Double.valueOf(result);
    }
    public static Double formattedDecimal(Double decimal) {
        if (decimal == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        String result = df.format(decimal);
        return Double.valueOf(result);
    }

    public static Double formattedDecimalOne(Double decimal) {
        if (decimal == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("######0.0");
        String result = df.format(decimal);
        return Double.valueOf(result);
    }

    public static double formattedDecimalOne(double decimal) {
        DecimalFormat df = new DecimalFormat("######0.0");
        String result = df.format(decimal);
        return Double.valueOf(result);
    }

    public static String doubelToPercentageStr(double decimal, int dots) {
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(dots);
        return nt.format(decimal).replaceAll(",", "");
    }

    public static Double doubelToPercentage(double decimal, int dots) {
        String perstr = doubelToPercentageStr(decimal, dots);
        return Double.valueOf(perstr.substring(0, perstr.length() - 1));
    }

    /**
     * 获取小时或分钟的时间格式
     *
     * @param second
     * @return
     */
    public static String getHourOrMinBySencond(long second) {
        if (second == 0) {
            return "0 min";
        }
        return String.valueOf(second / 60) + " min";
    }
    /**
     * 获取小时或分钟的时间格式
     *
     * @param second
     * @return
     */
    public static String getNewHourOrMinBySencond(long second) {
        String ret;
        if (second == 0) {
            return "";
        }
        if (second < 60 * 60) {
            // 显示分钟
            ret = String.valueOf(second / 60) + "分钟";
        } else {
            // 显示小时
            ret = String.valueOf(second / 3600) + "小时" + String.valueOf((second % 3600) / 60) + "分钟";
        }
        return ret;
    }

    public static String getHourOrMinBySencondNew(long second) {
        String ret;
        if (second == 0) {
            return "0";
        }
        if (second < 60 * 60) {
            // 显示分钟
            ret = String.valueOf(second / 60) + "分钟";
        } else {
            // 显示小时
            ret = String.valueOf(second / 3600) + "小时" + String.valueOf((second % 3600) / 60) + "分钟";
        }
        return ret;
    }

    public static String getMinBySencond(Long second) {
        if (second == null || second == 0) {
            return "0";
        }

        return String.valueOf(second / 60);
    }


    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date str2Date(String strDate, String pattern) {
        Date date = null;
        try {
            if (strDate != null && !"".equals(strDate.trim())) {
                return new SimpleDateFormat(pattern).parse(strDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 计算时间差
     *
     * @param dBefor 首日
     * @param dAfter 尾日
     * @return 时间差(毫秒)
     */
    public static final long getDateBetween(Date dBefor, Date dAfter) {
        long lBefor = 0;
        long lAfter = 0;
        long lRtn = 0;
        /** 取得距离 1970年1月1日 00:00:00 GMT 的毫秒数 */
        lBefor = dBefor.getTime();
        lAfter = dAfter.getTime();
        lRtn = lAfter - lBefor;
        return lRtn;
    }

    /**
     * 获取当前天最后时间
     *
     * @param time
     * @return
     * @throws Exception
     */
    public static Date getDayEnd(long time) throws Exception {
        Date date = new Date(time);
        SimpleDateFormat ymd = new SimpleDateFormat(YYYY_MM_DD);
        String str = ymd.format(date);
        SimpleDateFormat ymdhmd = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return ymdhmd.parse(str + " 23:59:59");
    }


    public static Date addTime(Date current, int val, Integer unit) {
        Calendar c = Calendar.getInstance();
        c.setTime(current);
        c.add(unit, val);
        return c.getTime();
    }

    /**
     * 获取某天开始时间毫秒数对应的结束时期毫秒数
     *
     * @param dayStart 每天开始的毫秒数
     * @return
     */
    public static Long getEndOfDay(Long dayStart) {
        return dayStart + (23 * 3600 + 59 * 60 + 59) * 1000;
    }

    /**
     * 获取本月第一天开始时间
     *
     * @return
     */
    public static Date getMonthStartTime() {
        return getMonthStartTime(0);
    }

    public static Date getMonthStartTime(int month) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getMonthEndTime(int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(getMonthStartTime(month + 1));
        c.add(Calendar.MILLISECOND, -1);
        return c.getTime();
    }

    public static Date getWeekStartTime() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_WEEK, 2);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getLastWeekStartTime(int week) {
        Calendar c = Calendar.getInstance();
        c.setTime(getDayByInput(week * 7));
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_WEEK, 2);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取N个月前的时间
     *
     * @return
     */
    public static Date getLastMonthByInput(int months) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, months);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 根据输入的天数获取当前时间的前/后几天
     *
     * @return Date
     */
    public static Date getDayByInput(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public static String getDayOrHourOrMinBySencond(long second) {
        String ret;
        if (second == 0) {
            return "0分钟";
        }
        if (second < 60 * 60) {
            // 显示分钟
            ret = String.valueOf(second / 60) + "分钟";
        } else if (second < 3600 * 24) {
            // 显示小时
            ret = String.valueOf(second / 3600) + "小时 " + String.valueOf((second % 3600) / 60) + "分钟";
        } else {
            ret = String.valueOf(second / (3600 * 24)) + "天 " + String.valueOf((second % (3600 * 24)) / 3600) + "小时 " + String.valueOf((second % 3600) / 60) + "分钟";
        }
        return ret;
    }

    public static Integer getZeroOfNullInt(Integer i) {
        return i == null ? 0 : i;
    }

    public static String getCarAge(Date date) {
        String ret;
        if (date == null || date.getTime() > new Date().getTime()) {
            return "车龄0年0月";
        }
        Calendar beforeCalendar = Calendar.getInstance();
        beforeCalendar.setTime(new Date(date.getTime() + 28800000));
        Calendar nowCalendar = Calendar.getInstance();

        int fromYear = beforeCalendar.get(Calendar.YEAR);
        int fromMonth = beforeCalendar.get(Calendar.MONTH);
        int toYear = nowCalendar.get(Calendar.YEAR);
        int toMonth = nowCalendar.get(Calendar.MONTH);
        int year = toYear - fromYear;
        int month = toMonth - fromMonth;
        if (month < 0) {
            year = year - 1;
            month = 12 + month;
        }
        if (year == 0 && month == 0) {
            ret = "车龄0年0月";
        } else if (year == 0 && month != 0) {
            ret = "车龄" + month + "月";
        } else if (year != 0 && month == 0) {
            ret = "车龄" + year + "年";
        } else {
            ret = "车龄" + year + "年" + month + "月";
        }

        return ret;
    }

    public static int getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        // 设置月份
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getLastDayOfMonthNew(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        // 设置月份
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        // 获取某月最大天数
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        calendar.set(Calendar.DATE, lastDay);
        return calendar.get(Calendar.DATE);
    }



    /**
     26      * 根据 年、月 获取对应的月份 的 天数
     27      */
    public static int getDaysByYearMonth(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     * @return
     */
    public static String getWeek(Date time) {
        String Week = "";
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int wek=c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "周日";
        }
        if (wek == 2) {
            Week += "周一";
        }
        if (wek == 3) {
            Week += "周二";
        }
        if (wek == 4) {
            Week += "周三";
        }
        if (wek == 5) {
            Week += "周四";
        }
        if (wek == 6) {
            Week += "周五";
        }
        if (wek == 7) {
            Week += "周六";
        }
        return Week;
    }

    public static Date currentDay(Long time) {
        // 获取东八区的YYYY-MM-DD
        time = time + 28800000;
        //秒
        time = time / 1000;
        //多少天
        Long dayTime = time / 86400;
        Long userDateTime = dayTime * 86400 * 1000;
        return new Date(userDateTime);
    }

    public static Integer currentYear(Long time) {
        // 获取东八区的YYYY-MM-DD
        time = time + 28800000;
        //秒
        time = time / 1000;
        //多少天
        Long dayTime = time / 86400;
        Long userDateTime = dayTime * 86400 * 1000;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(userDateTime));
        return c.get(Calendar.YEAR);
    }

    public static boolean carNightTimes(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour > 20 || hour < 5) {
            return true;
        }
        return false;
    }

    public static int betweenDays(Date start, Date end) {
        int days = 0;
        if(start != null && end != null) {
            long n1 = end.getTime();
            long n2 = start.getTime();
            long diff = n1 - n2;
            days = new Long(diff/(3600 * 1000 * 24)).intValue();
        }
        return days;
    }

    public static Date addDays(Date date, int addDays) {
        Calendar  calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, addDays);
        return calendar.getTime();
    }

    /**
     * 新增月份 到 当月最小时间
     * @param date
     * @param months
     * @return
     */
    public static Date addMonthsToFirstDay(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 新增月份 到 当月最大时间
     * @param date
     * @param months
     * @return
     */
    public static Date addMonthsToEndDay(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 新增月份
     * @param date
     * @param months
     * @return
     */
    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 两个日期相差月份
     * @param start
     * @param end
     * @return
     */
    public static int betweenMonths(Date start, Date end) {
        Calendar from = Calendar.getInstance();
        from.setTime(start);
        Calendar to = Calendar.getInstance();
        to.setTime(end);
        int fromYear = from.get(Calendar.YEAR);
        int toYear = to.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int toMonth = to.get(Calendar.MONTH);
        int month = (toYear - fromYear) * 12 + toMonth - fromMonth;
        return month;
    }

    /**
     * @param date
     * @param addDays
     * @return
     */
    public static Date addDaysToMax(Date date, int addDays) {
        Calendar  calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, addDays);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        return calendar.getTime();
    }

    public static String date2Str(Date date) {
        return date2Str(date, null);
    }

    public static String date2Str(Date date, String pattern, Integer zone) {
        return getSimpleDateFormatZone(pattern, ZMDateTimeUtil.getZoneId(zone)).format(date);
    }

    public static String date2Str(Date date, String pattern, String... zones) {
        if (date != null) {
            return getSimpleDateFormatZone(pattern, zones).format(date);
        }
        return null;
    }

    /**
     * 获取日期格式化方法
     *
     * @param pattern
     * @param zones
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormatZone(String pattern, String... zones) {
        SimpleDateFormat sf;
        if (pattern == null || pattern.trim().equals("")) {
            sf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        } else {
            sf = new SimpleDateFormat(pattern);
        }
        if (zones != null && zones.length > 0 && zones[0] != null) {
            sf.setTimeZone(TimeZone.getTimeZone(ZoneId.of(zones[0])));
        }

        return sf;
    }

    public static int getDateMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return Integer.valueOf(month) + 1;
    }

    public static int getDateYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public static Date getMonthStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getMonthEnd() {
        Date date = getMonthStart();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.getActualMaximum(5);
        Date endDate = addTime(date, dayofweek - 1, Calendar.DAY_OF_YEAR);
        cal.setTime(endDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        return calendar.getTime();
    }

    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Integer getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static void main(String[] args) {
        System.out.println(new Date(1680242858000l));
        System.out.println(addMonths(new Date(1680242858000l), 1));
        System.out.println("1234".substring(0, "1234".length()-1));
        System.out.println(getDateYear(new Date()));
        System.out.println(Arrays.asList(1,2).stream().map(s->String.valueOf(s)).collect(Collectors.joining("月,")));
        System.out.println(DateUtils.getDateMonth(new Date()));
        System.out.println(DateUtils.getMonthEnd(new Date()));
    }
}
