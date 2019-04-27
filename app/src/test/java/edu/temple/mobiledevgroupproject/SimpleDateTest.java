package edu.temple.mobiledevgroupproject;

import org.junit.Test;

import edu.temple.mobiledevgroupproject.Objects.SimpleDate;
import static org.junit.Assert.*;

public class SimpleDateTest {
    @Test
    public void simpleDate_InvalidMonthAndDay_ReturnsFalse() {
        assertFalse(SimpleDate.isValidDate(13, 32, 2019));
    }

    @Test
    public void simpleDate_InvalidMonth_ReturnsFalse() {
        assertFalse(SimpleDate.isValidDate(13, 2, 2019));
    }

    @Test
    public void simpleDate_InvalidDay_ReturnsFalse() {
        assertFalse(SimpleDate.isValidDate(1, 32, 2019));
    }

    @Test
    public void simpleDate_ValidDate_ReturnsTrue() {
        assertTrue(SimpleDate.isValidDate(1, 2, 2019));
    }

    @Test
    public void simpleDate_GetAgeString_ReturnsCorrectAge() {
        SimpleDate sd = new SimpleDate(10, 2, 1996);
        assertEquals(SimpleDate.getAgeString(sd), "22");
    }

    @Test
    public void simpleDate_GetAgeStringNonValidDate_ReturnsNull() {
        SimpleDate sd = new SimpleDate(23, 24, 2019);
        assertEquals(SimpleDate.getAgeString(sd), null);
    }

    @Test
    public void simpleDate_GetAgeStringNullSimpleDateObject_ReturnsNull() {
        assertEquals(SimpleDate.getAgeString(null), null);
    }
}