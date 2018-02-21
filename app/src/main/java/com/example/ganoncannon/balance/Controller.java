package com.example.ganoncannon.balance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ganoncannon on 11/30/17.
 */

public class Controller {
    private Profile user;
    private int counter;
    private String state;
    private int chosenDif;
    private int chosenSpeed;
    private int chosenTime;

    public Controller(String context, int maxVolume) {
        user = new Profile(this, context, maxVolume);
        chosenTime = user.getGoals().get("time");
        chosenDif = user.getGoals().get("difficulty");
        chosenSpeed = user.getGoals().get("speed");
        counter = chosenTime;
        state = "idle";
    }

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state, HashMap h) {
        this.state = state;
        user.getVoice().setState(state);
        if (state.equals("intro") || state.equals("outro") || state.equals("commands") || state.equals("paused"))
            user.getVoice().speak();
        if (state.equals("outro")) {
            // Finished the exercise either by stopping or finishing, create a data entry
            HashMap newEntry = new HashMap();
            newEntry.put("speed", chosenSpeed);
            newEntry.put("difficulty", chosenDif);
            newEntry.put("time", chosenTime);
            String week = Integer.toString(Calendar.WEEK_OF_YEAR);
            if (user.getData().getHistory().get(week) != null) {
                user.getData().getHistory().get(week).add(newEntry);
            } else {
                ArrayList init_runs = new ArrayList<HashMap<String, Integer>>();
                init_runs.add(newEntry);
                user.getData().getHistory().get(week).add(newEntry);
            }
            user.getData().writeData();
            this.state = "idle";
        } else if (state.equals("stopped")) {
            String week = Integer.toString(Calendar.WEEK_OF_YEAR);
            if (user.getData().getHistory().get(week) != null) {
                user.getData().getHistory().get(week).add(h);
            } else {
                ArrayList init_runs = new ArrayList<HashMap<String, Integer>>();
                init_runs.add(h);
                user.getData().getHistory().get(week).add(h);
            }
            user.getData().writeData();
            this.state = "idle";
        }
    }

    public int getChosenDif() {
        return chosenDif;
    }

    public void setChosenDif(int chosenDif) {
        this.chosenDif = chosenDif;
        user.getVoice().setChosenDif(chosenDif);
    }

    public int getChosenSpeed() {
        return chosenSpeed;
    }

    public void setChosenSpeed(int chosenSpeed) {
        if (chosenSpeed > 10)
            chosenSpeed = 10;
        this.chosenSpeed = chosenSpeed;
        user.getVoice().setChosenSpeed(chosenSpeed);
    }

    public int getChosenTime() {
        return chosenTime;
    }

    public void setChosenTime(int chosenTime) {
        if (chosenTime > 900)
            chosenTime = 900;
        this.chosenTime = chosenTime;
        user.getVoice().setChosenTime(chosenTime);
    }
}
