package com.example.ganoncannon.balance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextUnitTest {

    Controller controller = new Controller("no_context", 100);

    @Test
    public void testText() throws Exception {
        int size = 10;
        controller.getUser().setFontSize(size);
        assertEquals(25, (int)controller.getUser().getSettings().get("textSize"));
    }

    @Test
    public void testTextBounds() throws Exception {
        int size = 21;
        controller.getUser().setFontSize(size);
        assertEquals(35, (int)controller.getUser().getSettings().get("textSize"));
    }
}