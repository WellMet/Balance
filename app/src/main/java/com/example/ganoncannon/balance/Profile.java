package com.example.ganoncannon.balance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ganoncannon on 11/30/17.
 */

public class Profile {
    private Data data;
    private VoiceGeneration voice;
    private Controller controller;
    private HashMap<String, Integer> goals;
    private HashMap settings;
    public static final double RUN_WEIGHT = 0.50;
    public static final double TIME_WEIGHT = 0.25;
    public static final double SPEED_WEIGHT = 0.15;
    public static final double DIF_WEIGHT = 0.10;


    public Profile(Controller cont) {
        if (!loadSettings()) {
            settings = new HashMap();
            settings.put("textSize", 14);
            settings.put("fontType", "Georgia");
            ArrayList<String> init_objects = new ArrayList<String>();
            init_objects.add("red");
            init_objects.add("blue");
            init_objects.add("green");
            settings.put("objects", init_objects);
        }
        voice = new VoiceGeneration(settings);
        controller = cont;
        goals = new HashMap<String, Integer>();
        goals.put("attempts", 3);
        goals.put("difficulty", 1);
        goals.put("speed", 5);
        goals.put("time", 300);
        data = new Data(goals);
    }

    public boolean loadSettings() {
        return false;
    }

    public void writeSettings() {

    }

    public Data getData() {
        return data;
    }

    public void setData(Data Data) {
        this.data = Data;
    }

    public VoiceGeneration getVoice() {
        return voice;
    }

    public void setVoice(VoiceGeneration voice) {
        this.voice = voice;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public HashMap<String, Integer> getGoals() {
        return goals;
    }

    public void setGoals(HashMap goals) {
        this.goals = goals;
    }

    public HashMap getSettings() {
        return settings;
    }

    public void setSettings(HashMap settings) {
        this.settings = settings;
    }
}
