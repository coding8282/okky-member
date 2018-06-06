package org.okky.member.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateUtil {
    /**
     * @param time milli seconds.
     * @return yyyy-MM-dd: HH:mm:ss 형태로 반환한다.
     */
    static String toDateString(Long time) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
        Date date = new Date(time);
        return sdf.format(date);
    }
}
