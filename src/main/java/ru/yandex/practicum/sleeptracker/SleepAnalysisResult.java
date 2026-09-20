package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult<T> {
    private String description;
    private T result;

    public SleepAnalysisResult(String description, T result) {
        this.description = description;
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public T getResult() {
        return result;
    }
}
