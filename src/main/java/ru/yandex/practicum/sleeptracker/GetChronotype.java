package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class GetChronotype implements Function<List<SleepingSession>, SleepAnalysisResult<Chronotypes>> {
    @Override
    public SleepAnalysisResult<Chronotypes> apply(List<SleepingSession> sleepingSessionList) {
        Counters counters = new Counters();
        sleepingSessionList.stream()
                .filter(session -> {
                    if (session.getStartDateTime().getDayOfYear() < session.getEndDateTime().getDayOfYear()) return true;
                    else if (session.getStartDateTime().getHour() < 6) return true;
                    else return false;
                })
                .map(session -> {
                    if ((session.getStartDateTime().getHour() == 23 || session.getStartDateTime().getHour() < 6) && session.getEndDateTime().getHour() >= 9) counters.addOwlCounter();
                    else if (session.getStartDateTime().getHour() < 22 && session.getStartDateTime().getHour() > 6 && session.getEndDateTime().getHour() < 7) counters.addLarkCounter();
                    else counters.addPidginCounter();
                    return session;
                }).toList();
        Chronotypes result;
        if (sleepingSessionList.isEmpty()) return null;
        else if (counters.getOwlCounter() > counters.getLarkCounter() && counters.getOwlCounter() > counters.getPidginCounter()) result = Chronotypes.Сова;
        else if (counters.getLarkCounter() > counters.getOwlCounter() && counters.getLarkCounter() > counters.getPidginCounter()) result = Chronotypes.Жаворонок;
        else result = Chronotypes.Голубь;

        return new SleepAnalysisResult<>("GetChronotype возвращает принадлежность к хронотипу.", result);
    }
}
