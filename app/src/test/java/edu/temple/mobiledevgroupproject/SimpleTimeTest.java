package edu.temple.mobiledevgroupproject;

import org.junit.Test;

import edu.temple.mobiledevgroupproject.Objects.SimpleTime;
import static org.junit.Assert.*;

public class SimpleTimeTest {
    @Test
    public void simpleTime_InvalidTimeImpossibleInterval_ReturnsFalse() {
        SimpleTime start = new SimpleTime(11, 15, SimpleTime.POST_MERIDIEM);
        SimpleTime end = new SimpleTime(1, 45, SimpleTime.ANTE_MERIDIEM);
        assertFalse(SimpleTime.isValidTimeInterval(start, end));
    }

    @Test
    public void simpleTime_InvalidTimeSameTime_ReturnsFalse() {
        SimpleTime start = new SimpleTime(11, 15, SimpleTime.POST_MERIDIEM);
        SimpleTime end = new SimpleTime(11, 15, SimpleTime.POST_MERIDIEM);
        assertFalse(SimpleTime.isValidTimeInterval(start, end));
    }

    @Test
    public void simpleTime_ValidTime_ReturnsTrue() {
        SimpleTime start = new SimpleTime(11, 15, SimpleTime.ANTE_MERIDIEM);
        SimpleTime end = new SimpleTime(1, 45, SimpleTime.POST_MERIDIEM);
        assertTrue(SimpleTime.isValidTimeInterval(start, end));
    }

    @Test
    public void simpleTime_NullStart_ReturnsFalse() {
        assertEquals(SimpleTime.isValidTimeInterval(null, null), false);
    }

    @Test
    public void simpleTime_NullEnd_ReturnsFalse() {
        SimpleTime start = new SimpleTime(1, 15, SimpleTime.ANTE_MERIDIEM);
        assertEquals(SimpleTime.isValidTimeInterval(start, null), false);
    }

    @Test
    public void simpleTime_BothNull_ReturnsFalse() {
        SimpleTime end = new SimpleTime(1, 15, SimpleTime.POST_MERIDIEM);
        assertEquals(SimpleTime.isValidTimeInterval(null, end), false);
    }

    @Test
    public void simpleTime_FormatTime_ReturnsFormattedTime() {
        SimpleTime st = new SimpleTime(4, 0, SimpleTime.POST_MERIDIEM);
        assertEquals(SimpleTime.formatTime(st), "4:00 PM");
    }

    @Test
    public void simpleTime_FormatTimeNullTimeObj_ReturnsNull() {
        assertEquals(SimpleTime.formatTime(null), null);
    }
}