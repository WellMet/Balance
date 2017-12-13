package com.example.ganoncannon.balance;

import android.speech.tts.TextToSpeech;
import android.media.MediaPlayer;

/**
 * Created by ganoncannon on 11/14/17.
 */

public class VoiceGeneration {
    private Profile profile;
    private String script;

    public VoiceGeneration (Profile prof) {
        profile = prof;
        script = "";


    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
