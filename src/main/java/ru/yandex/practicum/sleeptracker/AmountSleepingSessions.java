package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AmountSleepingSessions implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionList) {
        return new SleepAnalysisResult("AmountSleepingSessions выводит количество сессий сна.", sleepingSessionList.size());
    }
}
