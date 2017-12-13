package com.example.ganoncannon.balance;

import java.util.ArrayList;

/**
 * Created by ganoncannon on 11/30/17.
 */

public class Profile {
    private Data data;
    private int textSize;
    private String fontType;
    private VoiceGeneration voice;
    private ArrayList<String> objects;
    private Controller controller;

    public Profile(Controller cont) {
        data = new Data();
        textSize = 14;
        fontType = "Georgia";
        voice = new VoiceGeneration(this);
        objects = new ArrayList<String>();
        objects.add("Red");
        objects.add("Blue");
        objects.add("Green");
        controller = cont;
    }

    public Profile(Controller cont, Boolean load) {
        loadData();
        loadSettings();
        voice = new VoiceGeneration(this);
    }

    public void loadData() {

    }

    public void loadSettings() {

    }

    public Data getData() {
        return data;
    }

    public void setData(Data Data) {
        this.data = Data;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public VoiceGeneration getVoice() {
        return voice;
    }

    public void setVoice(VoiceGeneration voice) {
        this.voice = voice;
    }

    public ArrayList<String> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<String> objects) {
        this.objects = objects;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
