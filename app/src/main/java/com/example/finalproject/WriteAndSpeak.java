package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WriteAndSpeak extends AppCompatActivity implements View.OnClickListener {
    Button talkBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writeandspeach);
        talkBtn = (Button) findViewById(R.id.speechButton);
    }

    @Override
    public void onClick(View v) {
        if (v == talkBtn)
            System.out.println("Ola");
    }
}
