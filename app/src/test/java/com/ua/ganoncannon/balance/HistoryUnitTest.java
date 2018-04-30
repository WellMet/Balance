package com.ua.ganoncannon.balance;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class HistoryUnitTest {

    Controller controller = new Controller("no_context", 100);

    @Test
    public void testHistory() throws Exception {
        // Test adding a simple run to the database
        populateTestData();
        String week = Integer.toString(Calendar.WEEK_OF_YEAR);
        assertEquals(5, (int) controller.getUser().getData().getHistory().get(week).get(0).get("speed"));
        assertEquals(1, (int) controller.getUser().getData().getHistory().get(week).get(0).get("difficulty"));
        assertEquals(900, (int) controller.getUser().getData().getHistory().get(week).get(0).get("time"));
    }

    @Test
    public void testProgress() throws Exception {
        // Add test data
        populateTestData();
        String week = Integer.toString(Calendar.WEEK_OF_YEAR);
        int runscore = (int)(0.5 * ((double)1/3) * 100);
        int timescore = (int)(0.25 * ((double)900/900) * 100);
        int speedscore = (int)(0.15 * ((double)5/5) * 100);
        int difscore = (int)(0.10 * ((double)2/2) * 100);

        int expectedScore = runscore + timescore + speedscore + difscore;
        assertEquals(expectedScore, controller.getUser().getData().getOverallProgress(week));
}

    public void populateTestData() {
        HashMap newEntry = new HashMap();
        newEntry.put("speed", 5);
        newEntry.put("difficulty", 1);
        newEntry.put("time", 900);
        String week = Integer.toString(Calendar.WEEK_OF_YEAR);
        if (controller.getUser().getData().getHistory().get(week) != null) {
            controller.getUser().getData().getHistory().get(week).add(newEntry);
        } else {
            ArrayList init_runs = new ArrayList<HashMap<String, Integer>>();
            init_runs.add(newEntry);
            controller.getUser().getData().getHistory().get(week).add(newEntry);
        }
    }
}