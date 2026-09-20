package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSleepingSession implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessionList) {
        long sumDurations = sleepingSessionList.stream()
                .map(session -> Duration.between(session.getStartDateTime(), session.getEndDateTime()))
                .mapToLong(Duration::toMinutes)
                .sum();
        try {
            return new SleepAnalysisResult<>("AverageSleepingSession возвращает среднюю сессию в минутах.", sumDurations / sleepingSessionList.size());
        } catch (Exception e) {
            System.out.println("Список сессий пуст");
            return null;
        }
    }
}
