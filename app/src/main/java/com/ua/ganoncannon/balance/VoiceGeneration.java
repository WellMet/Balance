package com.ua.ganoncannon.balance;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

/**
 * Created by ganoncannon on 11/14/17.
 */

public class VoiceGeneration implements TextToSpeech.OnInitListener {
    private HashMap settings;
    private ArrayList<String> intro;
    private String outro;
    private ArrayList<String> commands;
    private int commandIndex;
    private int chosenTime;
    private int chosenSpeed;
    private String state;
    private int chosenDif;
    private TextToSpeech tts;
    private Context context = null;
    public static final int COMMAND_DURATION = 4;
    public String SCRIPTFILE = "";

    public VoiceGeneration (HashMap settings) {
        this.settings = settings;
        intro = new ArrayList<String>();
        intro.add("3");
        intro.add("2");
        intro.add("1");
        outro = " Well Done!";
        state = "idle";
        chosenTime = 5 * 60;
        chosenSpeed = 5;
        chosenDif = 1;
        commands = new ArrayList<String>();
        commandIndex = 0;
    }

    public HashMap getSettings() {
        return settings;
    }

    public void setSettings(HashMap settings) {
        this.settings = settings;
    }

    public ArrayList<String> getIntro() {
        return intro;
    }

    public void setIntro(ArrayList<String> intro) {
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
        try {
            tts.setSpeechRate((1 + ((float) (chosenSpeed - 5) / 10)));
        } catch (Exception err) {

        }
    }

    public void setCommands(ArrayList<String> commands) {
        this.commands = commands;
    }

    public void generateOrdered() {
        // Function maps chosen speed (1 - 10) to a multiplier (0.5 - 1.5)
        commands.clear();
        double speedMult = 1 + ((double)(chosenSpeed - 5) / 10);
        String[] directions = {"Left", "Right"};
        boolean directionIndex = false;
        int objectIndex = 0;
        ArrayList<String> objects = (ArrayList<String>) settings.get("objects");
        // Add the downward trend
        for (int i = objects.size() - 1; i >= 0; i--) {
            objects.add(objects.get(i));
        }
        // New command duration:
        int duration = COMMAND_DURATION + (int)(COMMAND_DURATION * ((double)(5 - chosenSpeed) / 10));
        int numcommands = (int)((double)(chosenTime / duration)) + 10;

        // Generate the commands: direction + object 1,2,3,3,2,1; other_direction + object 1,2,3,3,2,1
        for(int i = 0; i < numcommands; i++) {
            commands.add(directions[directionIndex ? 1 : 0] + " foot, " + objects.get(objectIndex));
            if (objectIndex == objects.size() - 1) {
                objectIndex = 0;
                directionIndex = !directionIndex;
            }
            else
                objectIndex += 1;
        }
    }

    public void generateRandom() {
        commands.clear();
        double speedMult = 1 + ((double)(chosenSpeed - 5) / 10);
        String[] directions = {"Left", "Right"};
        ArrayList<String> objects = (ArrayList<String>) settings.get("objects");
        Random r1 = new Random();
        Random r2 = new Random();
        int objectIndex;
        int directionIndex;
        // New command duration:
        int duration = COMMAND_DURATION + (int)(COMMAND_DURATION * ((double)(5 - chosenSpeed) / 10));
        int numcommands = (int)((double)(chosenTime / duration)) + 10;

        for(int i = 0; i < numcommands; i++) {
            objectIndex = r1.nextInt(objects.size());
            directionIndex = r2.nextInt(2);
            commands.add(directions[directionIndex] + " foot, " + objects.get(objectIndex));
        }
    }

    public void speak() {
        if (tts != null) {
            tts.stop();
        }
        if (state.equals("intro")) {
            System.out.println("command index: " + commandIndex);
            System.out.println("activated intro");
            tts.speak(intro.get(commandIndex), TextToSpeech.QUEUE_ADD, null);
        } else if (state.equals("commands")) {
            System.out.println("activated commands");
            tts.speak(commands.get(commandIndex), TextToSpeech.QUEUE_ADD, null);
        } else if (state.equals("outro")) {
            System.out.println("activated outro");
            tts.speak(outro, TextToSpeech.QUEUE_ADD, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            tts.setPitch(0.85f);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                System.out.println("TTS: This Language is not supported");
            } else {
                speak();
            }
        } else {
            System.out.println("TTS Initilization Failed!");
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if (this.state.equals("commands") && state.equals("commands"))
            commandIndex++;
        else if(!this.state.equals("commands") && state.equals("commands")) {
            if (chosenDif == 1)
                generateOrdered();
            else if (chosenDif == 2)
                generateRandom();
            commandIndex = 0;
        } else if (this.state.equals("intro") && state.equals("intro")) {
            commandIndex++;
        } else if (!this.state.equals("intro") && state.equals("intro"))
            commandIndex = 0;
        this.state = state;
    }

    public int getChosenDif() {
        return chosenDif;
    }

    public void setChosenDif(int chosenDif) {
        this.chosenDif = chosenDif;
    }

    public TextToSpeech getTts() {
        return tts;
    }

    public void setTts(TextToSpeech tts) {
        this.tts = tts;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        SCRIPTFILE = context.getFilesDir().getPath()+ "script.wav";
        tts = new TextToSpeech(context, this);
    }
}
