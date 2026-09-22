package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadSleepingSessionCount implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionList) {
        long result = sleepingSessionList.stream()
                .filter(session -> session.getQuality() == SleepingQuality.BAD)
                .count();
        return new SleepAnalysisResult("BadSleepingSessionCount возвращает количество сессий с плохим сном.", result);
    }
}
