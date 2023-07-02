package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject.recyclerview.SentenceAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextToSpeech tts;
    FloatingActionButton speechButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speechButton = (FloatingActionButton) findViewById(R.id.speechButton);
        speechButton.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Sentence> sentences = new ArrayList<>();
        sentences.add(new Sentence("Frase de testeEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE"));
        sentences.add(new Sentence("Olá tudo bem?"));
        sentences.add(new Sentence("Frase de teste"));
        sentences.add(new Sentence("Frase de teste"));
        sentences.add(new Sentence("Frase de teste"));
        sentences.add(new Sentence("Frase de teste"));
        sentences.add(new Sentence("Frase de teste"));
        sentences.add(new Sentence("Frase de teste"));
        sentences.add(new Sentence("Frase de teste"));

        this.tts = new TextToSpeech(this, (status) -> {
            if (status != TextToSpeech.ERROR) {
                Locale pt = tts.getAvailableLanguages().stream().filter((l) ->
                        l.getCountry().equals("PT")).findFirst().orElse(Locale.ENGLISH);
                Log.i("[FINAL PROJECT]", pt.getLanguage());
                Log.i("FINAL PROJECT", tts.getAvailableLanguages().stream().map((l) -> l.getCountry()).collect(Collectors.toList()).toString());
                Log.i("FINAL PROJECT", String.valueOf(tts.setLanguage(pt)));
            } else {
                Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
            }
        });

        SentenceAdapter adapter = new SentenceAdapter(sentences, this.tts);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newSentence:
                this.writeAndSpeak();
                return true;
            case R.id.about:
                // todo
                Toast.makeText(this, "Unavailable", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == this.speechButton) {
            this.writeAndSpeak();
        }
    }

    private void writeAndSpeak() {
        startActivity(new Intent(MainActivity.this, WriteAndSpeak.class));
    }
}