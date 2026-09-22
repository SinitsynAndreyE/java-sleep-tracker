package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MaximumSleepingSession implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionList) {
        Optional<Duration> result = sleepingSessionList.stream()
                .map(session -> Duration.between(session.getStartDateTime(), session.getEndDateTime()))
                .max(Comparator.comparingLong(Duration::toMinutes));
        return new SleepAnalysisResult("MaximumSleepingSession возвращает максимальную сессию в минутах.", result.orElse(Duration.ofMinutes(-1)).toMinutes());
    }
}
