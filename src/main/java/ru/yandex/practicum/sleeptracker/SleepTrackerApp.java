package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SleepTrackerApp {
    //src/main/resources/sleep_log.txt
    public static List<Function<List<SleepingSession>, SleepAnalysisResult>> functionList = List.of(new AmountSleepingSessions(),
                                                                                                                new MinimumSleepingSession(),
                                                                                                                new MaximumSleepingSession(),
                                                                                                                new AverageSleepingSession(),
                                                                                                                new BadSleepingSessionCount(),
                                                                                                                new NoSleepNightCount(),
                                                                                                                new GetChronotype());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу:");
        Path path = Paths.get(scanner.nextLine());
        List<SleepingSession> sleepingSessionList;
        try (Stream<String> lines = Files.lines(path)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
            sleepingSessionList = lines
                                                        .map(line -> line.split(";"))
                                                        .map(line -> new SleepingSession(LocalDateTime.parse(line[0], formatter),
                                                                                                LocalDateTime.parse(line[1], formatter),
                                                                                                SleepingQuality.valueOf(line[2])))
                                                        .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (sleepingSessionList.isEmpty()) {
            System.out.println("Список сессий пуст");
        } else {
            List<SleepAnalysisResult> results = functionList.stream()
                    .map(func -> func.apply(sleepingSessionList))
                    .toList();

            results.stream()
                    .peek(result -> System.out.println(result.getDescription() + " Результат: " + result))
                    .toList();
        }
    }
}