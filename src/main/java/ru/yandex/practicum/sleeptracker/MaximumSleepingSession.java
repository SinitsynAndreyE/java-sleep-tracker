package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MaximumSleepingSession implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessionList) {
        Optional<Duration> result = sleepingSessionList.stream()
                .map(session -> Duration.between(session.getStartDateTime(), session.getEndDateTime()))
                .max(Comparator.comparingLong(Duration::toMinutes));
        try {
            return new SleepAnalysisResult<>("MaximumSleepingSession возвращает максимальную сессию в минутах.", result.orElseThrow(() -> new EmptyListException("Список сессий пуст")).toMinutes());
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
