package org.okky.member.util;

import lombok.experimental.FieldDefaults;
import org.junit.BeforeClass;
import org.junit.Test;
import org.okky.member.TestMother;

import java.util.Calendar;

import static lombok.AccessLevel.PUBLIC;
import static org.junit.Assert.assertEquals;

@FieldDefaults(level = PUBLIC)
public class DateUtilTest extends TestMother {
    static Calendar cal;

    @BeforeClass
    public static void setUp() {
        cal = Calendar.getInstance();
    }

    @Test
    public void toDateString_첫번째() {
        cal.set(1986, 2, 22, 5, 30, 12);
        long myBirth = cal.getTimeInMillis();

        String representation = DateUtil.toDateString(myBirth);

        assertEquals("내 생일 표현 일자가 다르다.", representation, "1986-03-22 05:30:12");
    }

    @Test
    public void toDateString_두번째() {
        cal.set(2000, 0, 1, 00, 00, 00);
        long millenium = cal.getTimeInMillis();

        String representation = DateUtil.toDateString(millenium);

        assertEquals("내 생일 표현 일자가 다르다.", representation, "2000-01-01 00:00:00");
    }
}