package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.finalproject.recyclerview.SentenceAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextToSpeech tts;
    FloatingActionButton newSentenceButton;

    SentenceManager sentenceManager;
    private List<Sentence> sentences;
    private SentenceAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            this.sentenceManager = new SentenceManager(this);

            newSentenceButton = (FloatingActionButton) findViewById(R.id.speechButton);
            newSentenceButton.setOnClickListener(this);

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            refreshAdapter();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        this.tts = TTS.getInstance(getApplicationContext()).getTTS();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            this.tts = TTS.getInstance(getApplicationContext()).getTTS();
            this.refreshAdapter();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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
                this.openAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == this.newSentenceButton) {
            this.writeAndSpeak();
        }
    }

    public void refreshAdapter() throws JSONException {
        sentences = this.sentenceManager.getSentences();
        adapter = new SentenceAdapter(sentences, this.tts, this);
        recyclerView.setAdapter(adapter);
    }

    private void writeAndSpeak() {
        Intent i = new Intent(MainActivity.this, FragmentActivity.class);
        i.putExtra("fragment", Constants.NEW_SENTENCE_MENU.value());
        startActivity(i);
    }

    private void openAbout() {
        Intent i = new Intent(MainActivity.this, FragmentActivity.class);
        i.putExtra("fragment", Constants.ABOUT_MENU.value());
        startActivity(i);
    }
}