package ru.yandex.practicum.sleeptracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GetChronotype implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionList) {
        HashMap<Chronotypes, Integer> counters = new HashMap<>();
        counters.put(Chronotypes.OWL, 0);
        counters.put(Chronotypes.LARK, 0);
        counters.put(Chronotypes.PIDGIN, 0);

        sleepingSessionList.stream()
                .filter(session -> {
                    if (session.getStartDateTime().getDayOfYear() < session.getEndDateTime().getDayOfYear()) return true;
                    else if (session.getStartDateTime().getHour() < 6) return true;
                    else return false;
                })
                .map(session -> {
                    if ((session.getStartDateTime().getHour() == 23 || session.getStartDateTime().getHour() < 6) && session.getEndDateTime().getHour() >= 9) counters.put(Chronotypes.OWL, counters.get(Chronotypes.OWL) + 1);
                    else if (session.getStartDateTime().getHour() < 22 && session.getStartDateTime().getHour() > 6 && session.getEndDateTime().getHour() < 7) counters.put(Chronotypes.LARK, counters.get(Chronotypes.LARK) + 1);
                    else counters.put(Chronotypes.PIDGIN, counters.get(Chronotypes.PIDGIN) + 1);
                    return session;
                }).toList();

        return new SleepAnalysisResult("GetChronotype возвращает принадлежность к хронотипу.", counters.entrySet().stream()
                                                                                                                        .max(Map.Entry.comparingByValue()).get().getKey());
    }
}
