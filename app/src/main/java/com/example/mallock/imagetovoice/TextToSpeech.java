package com.example.mallock.imagetovoice;

import android.content.Context;

/**
 * Created by Mallock on 6/22/2018.
 */

public class TextToSpeech {

    String self_text;
    int speech_speed;
    String language;
    Context context;
    String url;

    public TextToSpeech(Context context) {
        speech_speed = 0;
        language = "en-in";
        self_text = "";
        this.context = context;
    }

    public TextToSpeech setText(String text) {
        this.self_text = text;
        return this;
    }

    public TextToSpeech setSpeechSpeed(int speed) {
        if (speed > 10 || speed < -10) {
            throw new RuntimeException("Speech speed must be between -10 and +10");
        }
        this.speech_speed = speed;
        return this;
    }

    public TextToSpeech setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String build() {
        this.url = "http://api.voicerss.org/?key="+this.getApiKey()+"&hl="+this.language+"&src="+this.self_text+"&r="+this.speech_speed;
        return this.url;
    }

    private String getApiKey() {
        return context.getResources().getString(R.string.voice_rss_api_key);
    }

}
