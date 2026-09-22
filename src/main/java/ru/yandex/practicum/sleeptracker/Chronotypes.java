package ru.yandex.practicum.sleeptracker;

public enum Chronotypes {
    OWL("Сова"),
    LARK("Жаворонок"),
    PIDGIN("Голубь");

    private final String displayName;

    Chronotypes(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
