package com.example.finalproject;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class TTS {
    private static TextToSpeech tts;

    private TTS() {}

    public static TTS getInstance(Context context) {
        TTS instance = new TTS();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        tts = new TextToSpeech(context, (status) -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.FRENCH);
            } else {
                Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show();
            }
        });
        return instance;
    }

    public TextToSpeech getTTS() {
        return tts;
    }
}
