package com.ua.ganoncannon.balance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AudioUnitTest {

    Controller controller = new Controller("no_context", 100);

    @Test
    public void testAudio() throws Exception {
        int volume = 100;
        controller.getUser().setVolume(volume);
        assertEquals(100, (int)controller.getUser().getSettings().get("volume"));
    }

    @Test
    public void testAudioBounds() throws Exception {
        int volume = 101;
        controller.getUser().setVolume(volume);
        assertEquals(100, (int)controller.getUser().getSettings().get("volume"));
    }
}