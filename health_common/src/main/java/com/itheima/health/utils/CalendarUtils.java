package com.itheima.health.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**采用日历计算年龄：
 * 传相差的年龄参数，获取该年龄的（今天）此刻日期
 * calendar.add(Calendar.YEAR,-2),此时是2019-10-01，返回结果2017-10-01
 */
public class CalendarUtils {
    public static String getStringDate(Integer munber){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,munber);
        String name = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        return name;
    }
}
