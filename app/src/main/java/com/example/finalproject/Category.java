package com.example.finalproject;

import java.util.Arrays;

public enum Category {
    BASIC("Comunicação Básica"),
    QUESTION("Pergunta"),
    RESPONSE("Resposta");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public static Category fromString(String str) {
        return Arrays.stream(values()).filter((v) -> v.displayName.equalsIgnoreCase(str)).findFirst().orElse(null);
    }

    public String getDisplayName() {
        return displayName;
    }
}
