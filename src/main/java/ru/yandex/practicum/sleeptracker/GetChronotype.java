package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class GetChronotype implements Function<List<SleepingSession>, SleepAnalysisResult<String>> {
    @Override
    public SleepAnalysisResult<String> apply(List<SleepingSession> sleepingSessionList) {
        Counters counters = new Counters();
        sleepingSessionList.stream()
                .filter(session -> {
                    if (session.getStartDateTime().getDayOfYear() < session.getEndDateTime().getDayOfYear()) return true;
                    else if (session.getStartDateTime().getHour() < 6) return true;
                    else return false;
                })
                .map(session -> {
                    if ((session.getStartDateTime().getHour() == 23 || session.getStartDateTime().getHour() < 6) && session.getEndDateTime().getHour() >= 9) counters.owlCounter++;
                    else if (session.getStartDateTime().getHour() < 22 && session.getStartDateTime().getHour() > 6 && session.getEndDateTime().getHour() < 7) counters.larkCounter++;
                    else counters.pidginCounter++;
                    return session;
                }).toList();
        String result;
        if (sleepingSessionList.isEmpty()) return null;
        else if (counters.owlCounter > counters.larkCounter && counters.owlCounter > counters.pidginCounter) result = "Сова";
        else if (counters.larkCounter > counters.owlCounter && counters.larkCounter > counters.pidginCounter) result = "Жаворонок";
        else result = "Голубь";

        return new SleepAnalysisResult<>("GetChronotype возвращает принадлежность к хронотипу.", result);
    }
}

class Counters {
    public int owlCounter;
    public int larkCounter;
    public int pidginCounter;

    public Counters() {
        owlCounter = 0;
        larkCounter = 0;
        pidginCounter = 0;
    }
}
