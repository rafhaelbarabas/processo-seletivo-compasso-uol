package com.rafhael.andrade.processoseletivocompasso.enums;

public enum Gender {

    F("Female"),
    M("Male"),
    O("Others");

    private String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
