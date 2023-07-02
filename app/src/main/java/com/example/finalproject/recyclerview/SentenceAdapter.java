package com.example.finalproject.recyclerview;

import android.app.AlertDialog;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.Sentence;
import com.example.finalproject.SentenceManager;

import org.json.JSONException;

import java.util.List;

public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.SentenceViewHolder> {
    private static final String PACKAGE = "com.example.finalproject.recyclerview.SentenceAdapter";

    // Content to fill each item
    protected List<Sentence> sentences;
    protected TextToSpeech tts;
    private MainActivity act;

    public SentenceAdapter(List<Sentence> sentences, TextToSpeech tts, MainActivity act) {
        this.sentences = sentences;
        this.tts = tts;
        this.act = act;
    }

    @NonNull
    @Override
    public SentenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sentences, parent, false);
        return new SentenceViewHolder(v, tts, this.act);
    }

    @Override
    public void onBindViewHolder(@NonNull SentenceViewHolder holder, int position) {
        holder.sentenceContent.setText(this.sentences.get(position).getContent());
        holder.sentenceCategory.setText(this.sentences.get(position).getCategory().getDisplayName());
        holder.index = this.sentences.get(position).getIndex();
    }

    @Override
    public int getItemCount() {
        return sentences.size();
    }


    // Set the view for each item
    public static class SentenceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView sentenceContent, sentenceCategory;
        int index;
        TextToSpeech tts;

        private SentenceManager manager;
        private MainActivity act;

        public SentenceViewHolder(@NonNull View itemView, TextToSpeech tts, MainActivity act) {
            super(itemView);
            this.sentenceContent = (TextView) itemView.findViewById(R.id.sentenceContent);
            this.sentenceCategory = (TextView) itemView.findViewById(R.id.sentenceCategory);
            this.tts = tts;
            this.act = act;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            try {
                this.manager = new SentenceManager(itemView.getContext());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onClick(View v) {
            this.tts.speak(this.sentenceContent.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, PACKAGE);
        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            builder.setMessage("Eliminar frase?")
                    .setTitle("Frase");

            builder.setPositiveButton("Eliminar", (dialog, id) -> {
                try {
                    this.manager.deleteSentence(this.index);
                    dialog.dismiss();
                    this.act.refreshAdapter();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });

            builder.setNegativeButton("Cancelar", (dialog, id) -> {
                dialog.cancel();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }
    }

}
