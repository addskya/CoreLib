package cn.orange.core.util;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Orange on 18-11-21.
 * Email:addskya@163.com
 */
public class DateUtil {
    private static final String YEAR_MONTH_DATE_LINE = "yyyy-MM-dd";

    private static final String YEAR_MONTH_DATE_HOUR_MINUTE_LINE = "yyyy-MM-dd HH:mm";

    private static final String YEAR_MONTH_DATE_HOUR_MINUTE_DOT = "yyyy.MM.dd HH:mm";

    private static final String YEAR_MONTH_DATE_HOUR_MINUTE_SECOND_LINE = "yyyy-MM-dd HH:mm:ss";

    // 设置一个最小时间,避免服务器上有些时间有毫秒,有些时间有秒这种情况
    private static long MIN_TIME_LIMIT;

    static {
        Calendar min = Calendar.getInstance(Locale.CHINA);
        min.set(2018, 1, 1);
        MIN_TIME_LIMIT = min.getTimeInMillis();
    }

    /**
     * 转换时间戳
     *
     * @param timeInMills 时间戳
     * @return yyyy-MM-dd
     */
    public static String toHumanDate(long timeInMills) {
        return format(timeInMills, YEAR_MONTH_DATE_LINE);
    }

    /**
     * 转换时间戳
     *
     * @param date 时间
     * @return yyyy-MM-dd
     */
    public static String toHumanDate(Date date) {
        return format(date.getTime(), YEAR_MONTH_DATE_LINE);
    }

    /**
     * 日期转换为字符表达
     *
     * @param date 日期转
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toHumanDateFull(@NonNull Date date) {
        return format(date.getTime(), YEAR_MONTH_DATE_HOUR_MINUTE_SECOND_LINE);
    }


    /**
     * 日期转换为字符表达
     *
     * @param timeInMills 日期转
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toHumanDateFull(@NonNull long timeInMills) {
        return format(timeInMills, YEAR_MONTH_DATE_HOUR_MINUTE_SECOND_LINE);
    }

    public static long now() {
        return System.currentTimeMillis();
    }


    public static Date toDate(long timeInMills) {
        return new Date(timeInMills);
    }

    /**
     * 转换时间戳
     *
     * @param time 时间戳
     * @return yyyy.MM.dd HH:mm
     */
    public static String timeDotToString(long time) {
        return format(time, YEAR_MONTH_DATE_HOUR_MINUTE_DOT);
    }

    private static String format(long time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        time = time * (time <= MIN_TIME_LIMIT ? 1000 : 1);
        return simpleDateFormat.format(new Date(time));
    }
}
