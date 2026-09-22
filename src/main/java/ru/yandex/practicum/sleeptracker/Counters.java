package ru.yandex.practicum.sleeptracker;

public class Counters {
    private int owlCounter;
    private int larkCounter;
    private int pidginCounter;

    public Counters() {
        owlCounter = 0;
        larkCounter = 0;
        pidginCounter = 0;
    }

    public int getOwlCounter() {
        return owlCounter;
    }

    public int getLarkCounter() {
        return larkCounter;
    }

    public int getPidginCounter() {
        return pidginCounter;
    }

    public void addOwlCounter() {
        owlCounter++;
    }

    public void addLarkCounter() {
        larkCounter++;
    }

    public void addPidginCounter() {
        pidginCounter++;
    }
}