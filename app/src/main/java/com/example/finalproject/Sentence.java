package com.example.finalproject;

public class Sentence {
    private String content;
    private Category category;
    private int index;

    public Sentence(String content, Category category) {
        this(content, category, -1);
    }

    public Sentence(String content, Category category, int index) {
        this.content = content;
        this.category = category;
        this.index = index;
    }

    public String getContent() {
        return content;
    }

    public Category getCategory() {
        return category;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
