package com.example.ganoncannon.balance;

import android.speech.tts.TextToSpeech;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ganoncannon on 11/14/17.
 */

public class VoiceGeneration {
    private HashMap settings;
    private String intro;
    private String outro;
    private ArrayList<String> commands;
    private int chosenTime;
    private int chosenSpeed;
    public static final int COMMAND_DURATION = 4;

    public VoiceGeneration (HashMap settings) {
        this.settings = settings;
        intro = "";
        outro = "";
        commands = new ArrayList<String>();
    }

    public HashMap getSettings() {
        return settings;
    }

    public void setSettings(HashMap settings) {
        this.settings = settings;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getOutro() {
        return outro;
    }

    public void setOutro(String outro) {
        this.outro = outro;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public int getChosenTime() {
        return chosenTime;
    }

    public void setChosenTime(int chosenTime) {
        this.chosenTime = chosenTime;
    }

    public int getChosenSpeed() {
        return chosenSpeed;
    }

    public void setChosenSpeed(int chosenSpeed) {
        this.chosenSpeed = chosenSpeed;
    }

    public void setCommands(ArrayList<String> commands) {
        this.commands = commands;
    }

    public void generateOrdered() {
        // Function maps chosen speed (1 - 10) to a multiplier (0.5 - 1.5)
        double speedMult = 1 + ((chosenSpeed - 5) / 10);
        String[] directions = {"Left", "Right"};
        int directionIndex = 0;
        int objectIndex = 0;
        ArrayList<String> objects = (ArrayList<String>) settings.get("objects");
        for(int i = 0; i < (int)(chosenTime / (COMMAND_DURATION * speedMult)); i++) {
            commands.add(directions[directionIndex] + "foot," + objects.get(objectIndex));
            if (objectIndex < 2)
                objectIndex++;
            else {
                objectIndex = 0;
                if (directionIndex < 1)
                    directionIndex++;
                else
                    directionIndex--;
            }
        }
    }
}
