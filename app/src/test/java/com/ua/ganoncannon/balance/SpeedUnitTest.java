package com.ua.ganoncannon.balance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpeedUnitTest {

    Controller controller = new Controller("no_context", 100);

    @Test
    public void testSpeed() throws Exception {
        int speedProgress = 6;
        controller.setChosenSpeed(speedProgress);
        assertEquals(6, controller.getChosenSpeed());
    }

    @Test
    public void testSpeedBounds() throws Exception {
        int speedProgress = 11;
        controller.setChosenSpeed(speedProgress);
        assertEquals(10, controller.getChosenSpeed());
    }
}