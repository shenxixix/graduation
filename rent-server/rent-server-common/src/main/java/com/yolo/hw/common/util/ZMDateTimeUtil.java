package com.yolo.hw.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author:shenxi
 * @Des:
 * @Date:2017/11/16
 */
public class ZMDateTimeUtil {
    /**
     * yyyy 年
     * MM 月
     * dd 天
     * HH 24小时制
     * mm 分钟
     * ss 秒
     */
    private final static String DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据时区偏移量格式化时间
     *
     * @param dateTime 当前时间
     * @param offset   偏移量 28800为北京时间
     * @return 返回格式化后的时间
     */
    public static String formMatter(Date dateTime, int offset) {
        return formMatter(dateTime, offset, DEFAULT);
    }
    public static String formMatter28800(Date dateTime, int offset) {
        return formMatter(dateTime, offset, "yyyy-MM-dd");
    }
    public static String formMatter28800Month(Date dateTime, int offset) {
        return formMatter(dateTime, offset, "yyyy-MM");
    }

    public static String formMatter28800Day(Date dateTime, int offset) {
        return formMatter(dateTime, offset, "MM月dd日");
    }

    public static String formMatter28800INT(Date dateTime, int offset) {
        return formMatter(dateTime, offset, "MMdd");
    }
    public static String formMatter28800Min(Date dateTime, int offset) {
        return formMatter(dateTime, offset, "HH:mm:ss");
    }

    /**
     * 根据时区偏移量格式
     *
     * @param dateTime 当前时间
     * @param offset   偏移量 28800为北京时间
     * @param fmt      默认yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formMatter(Date dateTime, int offset, String fmt) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(dateTime.toInstant(), zoneOffset);
        return localDateTime.format(DateTimeFormatter.ofPattern(fmt == null || "".equals(fmt) ? DEFAULT : fmt));
    }


    /**
     * 获取一天第一个和最后个时间
     *
     * @param date   当前时间
     * @param offset 当前时间时区偏移量 北京时间28800
     * @return 返回 0:年月日00:00:00.000 1:年月日23:59:59.999
     */
    public static Date[] getStartAndEndDay(Date date, int offset) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneOffset);
        localDateTime = localDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999);
        Instant instant = localDateTime.atZone(zoneOffset).toInstant();
        Date end = Date.from(instant);
        Date start = new Date(end.getTime() - 86400000 + 1000);
        Date[] startAndEndDay = new Date[2];
        startAndEndDay[0] = start;
        startAndEndDay[1] = end;
        return startAndEndDay;
    }
    public static Date[] getStartAndEndDayOfMonth(Date date, int offset) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneOffset);
        LocalDateTime firstDay =localDateTime.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(001);
        Date start = Date.from(firstDay.atZone(zoneOffset).toInstant());

        LocalDateTime lastDay =localDateTime.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999);
        Date end = Date.from(lastDay.atZone(zoneOffset).toInstant());

        Date[] startAndEndDayOfMonth = new Date[2];
        startAndEndDayOfMonth[0] = start;
        startAndEndDayOfMonth[1] = end;
        return startAndEndDayOfMonth;
    }


    public static Date plusDays(Date date, long days, int offset) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneOffset);
        localDateTime = localDateTime.plusDays(days);
        Instant instant = localDateTime.atZone(zoneOffset).toInstant();
        return Date.from(instant);
    }

    public static Date plusHours(Date date, long hours, int offset) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneOffset);
        localDateTime = localDateTime.plusHours(hours);
        Instant instant = localDateTime.atZone(zoneOffset).toInstant();
        return Date.from(instant);
    }

    public static String getZoneId(int offset) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        return zoneOffset.getId();
    }

    public static String getCarAge(Date date) {
        String ret;
        if (date.getTime() > new Date().getTime()) {
            return "";
        }
        Calendar beforeCalendar = Calendar.getInstance();
        beforeCalendar.setTime(date);
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
            ret = "";
        } else if (year == 0 && month != 0) {
            ret = "车龄" + month + "月";
        } else if (year != 0 && month == 0) {
            ret = "车龄" + year + "年";
        } else {
            ret = "车龄" + year + "年" + month + "月";
        }

        return ret;
    }
    public static String paserDate(Calendar calendar) {
        String dayStr= "";
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day == 1 ) {
            dayStr = "monday";
        } else if (day == 2) {
            dayStr = "tuesday";
        } else if (day == 3) {
            dayStr = "wednesday";
        } else if (day == 4) {
            dayStr = "thursday";
        } else if (day == 5) {
            dayStr = "friday";
        } else if (day == 6) {
            dayStr = "saturday";
        } else {
            dayStr = "sunday";
        }
        return dayStr;
    }
    public static void main(String[] args) {




        //++++++++++时间戳+++++++++++++++++++++
        Date date = new Date();
        System.out.println("当前时区偏移量：" + ZMDateTimeUtil.formMatter(date, 28800));
        Date[] d = ZMDateTimeUtil.getStartAndEndDay(date, 28800);
        System.out.println("当前天>>起:" + ZMDateTimeUtil.formMatter(d[0], 28800) + ";止:" + ZMDateTimeUtil.formMatter(d[1], 28800));
        System.out.println("当前时刻+5天:" + ZMDateTimeUtil.plusDays(new Date(), 5, 28800));
        Date[] d1 = ZMDateTimeUtil.getStartAndEndDayOfMonth(date, 28800);
        System.out.println("当前月第一和最后一个时间:" + ZMDateTimeUtil.formMatter(d1[0], 28800) + ";止:" + ZMDateTimeUtil.formMatter(d1[1], 28800));
        //+++++++++++++++++++++++++++++++
    }

}
