package com.example.finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        short value = getIntent().getExtras().getShort("fragment");
        Fragment f = null;

        switch (Constants.fromValue(value)) {
            case NEW_SENTENCE_MENU:
                f = new NewSentenceFragment();
                break;
            case ABOUT_MENU:
                f = new AboutFragment();
        }

        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().add(android.R.id.content, f).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}