package com.example.finalproject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.databinding.NewSentenceBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class NewSentenceFragment extends Fragment implements View.OnClickListener {
    private static final String PACKAGE = "com.example.finalproject.NewSentenceFragment";
    private EditText editText;
    private Button saveButton, previewButton;
    private Spinner categorySpinner;
    private TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return NewSentenceBinding.inflate(inflater).getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.editText = view.findViewById(R.id.sentenceText);
        this.saveButton = view.findViewById(R.id.saveButton);
        this.saveButton.setOnClickListener(this);
        this.previewButton = view.findViewById(R.id.previewButton);
        this.previewButton.setOnClickListener(this);
        this.categorySpinner = view.findViewById(R.id.category);

        this.tts = TTS.getInstance(view.getContext()).getTTS();

        this.categorySpinner.setAdapter(new ArrayAdapter<>(
                this.getContext(), android.R.layout.simple_spinner_dropdown_item,
                Arrays.stream(Category.values()).map((c) -> c.getDisplayName()).collect(Collectors.toList())));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            String content = this.editText.getText().toString();
            String category = (String) this.categorySpinner.getSelectedItem();
            try {
                SentenceManager manager = new SentenceManager(v.getContext());
                manager.addSentence(new Sentence(content, Category.fromString(category)));
                Toast.makeText(v.getContext(), "Frase criada", Toast.LENGTH_SHORT).show();
                ((Activity) v.getContext()).finish();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else if (v.getId() == R.id.previewButton) {
            tts.speak(this.editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, PACKAGE);
        }
    }
}
