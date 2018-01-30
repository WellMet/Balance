package com.example.ganoncannon.balance;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

/**
 * Created by ganoncannon on 11/14/17.
 */

public class VoiceGeneration implements TextToSpeech.OnInitListener {
    private HashMap settings;
    private String intro;
    private String outro;
    private ArrayList<String> commands;
    private int chosenTime;
    private int chosenSpeed;
    private String state;
    private int chosenDif;
    private TextToSpeech tts;
    private MediaPlayer mp;
    private Context context = null;
    public static final int COMMAND_DURATION = 4;
    public static final String SCRIPTFILE = "script.wav";

    public VoiceGeneration (HashMap settings) {
        this.settings = settings;
        intro = "Ready in 3. 2. 1.";
        outro = "... Great job!";
        state = "idle";
        chosenTime = 5 * 60;
        chosenSpeed = 5;
        chosenDif = 1;
        commands = new ArrayList<String>();
        mp = new MediaPlayer();
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
        commands.clear();
        double speedMult = 1 + ((chosenSpeed - 5) / 10);
        String[] directions = {"Left", "Right"};
        boolean directionIndex = false;
        int objectIndex = 0;
        ArrayList<String> objects = (ArrayList<String>) settings.get("objects");
        // Add the downward trend
        for (int i = objects.size() - 1; i >= 0; i--) {
            objects.add(objects.get(i));
        }

        // Generate the commands: direction + object 1,2,3,3,2,1; other_direction + object 1,2,3,3,2,1
        for(int i = 0; i < (int)((chosenTime * speedMult) / (COMMAND_DURATION)); i++) {
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
        double speedMult = 1 + ((chosenSpeed - 5) / 10);
        String[] directions = {"Left", "Right"};
        ArrayList<String> objects = (ArrayList<String>) settings.get("objects");
        Random r1 = new Random();
        Random r2 = new Random();
        int objectIndex;
        int directionIndex;
        for(int i = 0; i < (int)((chosenTime * speedMult) / (COMMAND_DURATION)); i++) {
            objectIndex = r1.nextInt(objects.size());
            directionIndex = r2.nextInt(2);
            commands.add(directions[directionIndex] + " foot, " + objects.get(objectIndex));
        }
    }

    public void speak() {
        // TODO: add silence utterances between 3.2.1
        float speedMult = 1 + ((chosenSpeed - 5) / 10);
        speedMult = (float) 0.7 * speedMult;
        if (tts != null) {
            tts.stop();
        }
        if (state.equals("intro")) {
            System.out.println("activated intro");
            tts.setSpeechRate(speedMult);
            tts.speak(intro, TextToSpeech.QUEUE_FLUSH, null);
        } else if (state.equals("commands")) {
            System.out.println("activated outro");
            setupCommands();
        } else if (state.equals("outro")) {
            System.out.println("activated outro");
            tts.setSpeechRate(speedMult);
            tts.speak(outro, TextToSpeech.QUEUE_FLUSH, null);
        } else if (state.equals("paused")) {
            mp.pause();
        } else if (state.equals("resume")) {
            mp.start();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
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
        tts = new TextToSpeech(context, this);
    }

    public void setupCommands() {
        // Build the script string
        if (chosenDif == 1) {
            generateOrdered();
        } else if (chosenDif == 2) {
            generateRandom();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < commands.size() - 1; i++) {
            sb.append(commands.get(i));
            sb.append(", ");
        }

        // Generate the tts to file and start the audio
        tts.synthesizeToFile(sb.toString(), null, SCRIPTFILE);
        tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
            @Override
            public void onUtteranceCompleted(String utteranceId) {
                if (state.equals("commands")) {
                    try {
                        mp.setDataSource(SCRIPTFILE);
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
