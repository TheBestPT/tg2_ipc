package com.example.finalproject;

import java.util.Arrays;

public enum Constants {
    NEW_SENTENCE_MENU((short) 1),
    ABOUT_MENU((short) 2);

    private final short correspondingValue;

    Constants(short value) {
        this.correspondingValue = value;
    }

    public static Constants fromValue(short i) {
        return Arrays.stream(values()).filter((v) -> v.value() == i).findFirst().orElse(null);
    }

    public short value() {
        return this.correspondingValue;
    }
}
