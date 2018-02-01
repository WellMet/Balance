package com.example.ganoncannon.balance;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ganoncannon on 11/30/17.
 */

public class Profile implements Serializable{
    private Data data;
    private VoiceGeneration voice;
    private Controller controller;
    private HashMap<String, Integer> goals;
    private HashMap settings;
    private String context;
    public static final double RUN_WEIGHT = 0.50;
    public static final double TIME_WEIGHT = 0.25;
    public static final double SPEED_WEIGHT = 0.15;
    public static final double DIF_WEIGHT = 0.10;


    public Profile(Controller cont, String context, int maxVolume) {
        this.context = context;
        if (!readSettings()) {
            settings = new HashMap();
            settings.put("textSize", 30);
            settings.put("fontType", "Georgia");
            ArrayList<String> init_objects = new ArrayList<String>();
            init_objects.add("green");
            init_objects.add("blue");
            init_objects.add("red");
            settings.put("objects", init_objects);
            settings.put("volume", maxVolume);
            settings.put("brightness", 255);
            settings.put("maxVolume", maxVolume);
        }
        voice = new VoiceGeneration(settings);
        controller = cont;
        goals = new HashMap<String, Integer>();
        goals.put("attempts", 3);
        goals.put("difficulty", 1);
        goals.put("speed", 5);
        goals.put("time", 300);
        data = new Data(goals, context);
    }

    public void writeSettings() {
        try {
            FileOutputStream fstream = new FileOutputStream(context + "settings.ser");
            ObjectOutputStream ostream = new ObjectOutputStream(fstream);
            ostream.writeObject(settings);
            ostream.close();
            fstream.close();
            System.out.println("write settings success");
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public boolean readSettings() {
        try {
            FileInputStream istream = new FileInputStream(context + "settings.ser");
            ObjectInputStream ostream = new ObjectInputStream(istream);
            settings = (HashMap) ostream.readObject();
            ostream.close();
            istream.close();
            System.out.println("read settings success");
            return true;
        } catch (IOException err){
            err.printStackTrace();
            return false;
        } catch (ClassNotFoundException cErr) {
            cErr.printStackTrace();
            return false;
        }
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

    public void setFontSize(int size) {
        // Convert from seekbar to dp
        settings.put("textSize", size + 15);
        writeSettings();
    }

    public void setObject(String s, int index) {
        ArrayList<String> objects = (ArrayList<String>) settings.get("objects");
        if (objects != null) {
            objects.set(index, s);
            settings.put("objects", objects);
        }
        writeSettings();
    }

    public void setVolume(int v) {
        settings.put("volume", v);
        writeSettings();
    }

    public void setBrightness(int b) {
        settings.put("brightness", b);
        writeSettings();
    }
}
