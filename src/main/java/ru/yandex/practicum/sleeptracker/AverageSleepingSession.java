package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSleepingSession implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionList) {
        double avgDurations = sleepingSessionList.stream()
                .map(session -> Duration.between(session.getStartDateTime(), session.getEndDateTime()))
                .mapToLong(Duration::toMinutes)
                .average().orElse(0);
        return new SleepAnalysisResult("AverageSleepingSession возвращает среднюю сессию в минутах.", avgDurations);
    }
}
