package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SentenceManager {
    private static final String PACKAGE_NAME = "com.example.finalproject.preferences";
    private final SharedPreferences sharedPref;
    private final SharedPreferences.Editor preferencesEditor;
    
    public SentenceManager(Context context) throws JSONException {
        this.sharedPref = context.getSharedPreferences(PACKAGE_NAME ,Context.MODE_PRIVATE);
        this.preferencesEditor = sharedPref.edit();

        if (this.sharedPref.getString("json", null) == null) {
            JSONObject obj = new JSONObject();
            obj.put("currentIndex", 0);
            obj.put("array", new JSONArray());
            this.preferencesEditor.putString("json", obj.toString());
            this.preferencesEditor.commit();
        }
    }

    public List<Sentence> getSentences() throws JSONException {
        String json = this.sharedPref.getString("json", null);
        if (json == null) throw new JSONException("JSON not initialized");

        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("array");
        List<Sentence> toReturn = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject sentence = arr.getJSONObject(i);
            toReturn.add(new Sentence(
                    sentence.getString("content"),
                    Category.valueOf(sentence.getString("category")),
                    sentence.getInt("index")
            ));
        }

        return toReturn;
    }

    public void addSentence(Sentence sentence) throws JSONException {
        String json = this.sharedPref.getString("json", null);
        if (json == null) throw new JSONException("JSON not initialized");

        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("array");

        JSONObject sentenceObj = new JSONObject();
        int currentIndex = obj.getInt("currentIndex");
        sentenceObj.put("index", currentIndex);
        sentenceObj.put("content", sentence.getContent());
        sentenceObj.put("category", sentence.getCategory().toString());
        arr.put(sentenceObj);

        obj.put("currentIndex", currentIndex + 1);
        obj.put("array", arr);
        this.preferencesEditor.putString("json", obj.toString());
        this.preferencesEditor.commit();
    }

    public void deleteSentence(int index) throws JSONException {
        String json = this.sharedPref.getString("json", null);
        if (json == null) throw new JSONException("JSON not initialized");

        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("array");
        int currentIndex = obj.getInt("currentIndex");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject sentence = arr.getJSONObject(i);
            if (sentence.getInt("index") == index) {
                arr.remove(i);
            }
        }

        obj.put("currentIndex", currentIndex - 1);
        obj.put("array", arr);
        this.preferencesEditor.putString("json", obj.toString());
        this.preferencesEditor.commit();
    }
}
