package ru.yandex.practicum.sleeptracker;

import java.time.Period;
import java.util.List;
import java.util.function.Function;

public class NoSleepNightCount implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessionList) {
        try {
            Period period = Period.between(sleepingSessionList.getFirst().getStartDateTime().toLocalDate(), sleepingSessionList.getLast().getEndDateTime().toLocalDate());
            int amountOfNights = period.getDays();
            if (sleepingSessionList.getFirst().getStartDateTime().getHour() < 12) {
                amountOfNights += 1;
            }
            int sleepNightsCount = sleepingSessionList.stream()
                    .mapToInt(session -> {
                        if (session.getStartDateTime().getDayOfYear() < session.getEndDateTime().getDayOfYear()) return 1;
                        else if (session.getStartDateTime().getHour() < 6) return 1;
                        else return 0;
                    })
                    .sum();
            return new SleepAnalysisResult<>("NoSleepNightCount возвращает количество бессонных ночей.", amountOfNights - sleepNightsCount);
        } catch (Exception e) {
            System.out.println("Список сессий пуст");
            return null;
        }
    }
}

