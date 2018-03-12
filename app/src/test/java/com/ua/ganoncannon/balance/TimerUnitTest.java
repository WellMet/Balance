package com.ua.ganoncannon.balance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimerUnitTest {

    Controller controller = new Controller("no_context", 100);

    @Test
    public void testTime() throws Exception {
        int min = 5;
        int sec = 0;
        controller.setChosenTime((min * 60) + sec);
        assertEquals(300, controller.getChosenTime());
    }

    @Test
    public void testTimeBounds() throws Exception {
        int min = 15;
        int sec = 1;
        controller.setChosenTime((min * 60) + sec);
        assertEquals(900, controller.getChosenTime());
    }
}