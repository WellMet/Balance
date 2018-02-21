package com.example.ganoncannon.balance;

import org.junit.Test;

import static org.junit.Assert.*;

public class ButtonUnitTest {

    Controller controller = new Controller("no_context", 100);

    @Test
    public void testDif() throws Exception {
        boolean dif = true;
        controller.setChosenDif(dif ? 2 : 1);
        assertEquals(2, controller.getChosenDif());
    }

    @Test
    public void testState() throws Exception {
        String state = "running";
        controller.setState(state, null);
        assertEquals("running", controller.getState());
    }
}