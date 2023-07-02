package com.example.finalproject.recyclerview;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.Sentence;

import java.util.List;

public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.SentenceViewHolder> {
    private static final String PACKAGE = "com.example.finalproject.recyclerview.SentenceAdapter";

    // Content to fill each item
    protected List<Sentence> sentences;
    protected TextToSpeech tts;

    public SentenceAdapter(List<Sentence> sentences, TextToSpeech tts) {
        this.sentences = sentences;
        this.tts = tts;
    }

    @NonNull
    @Override
    public SentenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sentences, parent, false);
        return new SentenceViewHolder(v, tts);
    }

    @Override
    public void onBindViewHolder(@NonNull SentenceViewHolder holder, int position) {
            holder.sentenceContent.setText(this.sentences.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return sentences.size();
    }


    // Set the view for each item
    public static class SentenceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView sentenceContent;
        TextToSpeech tts;
        public SentenceViewHolder(@NonNull View itemView, TextToSpeech tts) {
            super(itemView);
            this.sentenceContent = (TextView) itemView.findViewById(R.id.sentenceContent);
            this.tts = tts;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // this.tts.speak(this.sentenceContent.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            this.tts.speak(this.sentenceContent.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, PACKAGE);
        }
    }

}
