package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleepTrackerAppTest {

    private static List<SleepingSession> sleepingSessionList;

    @BeforeEach
    public void sleepingSessionList() {
        Path path = Paths.get("src/main/resources/sleep_log.txt");
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
        }
    }

    @Test
    public void testAmountSleepingSessionsEmptyList() {
        sleepingSessionList = new ArrayList<>();
        AmountSleepingSessions function = new AmountSleepingSessions();
        SleepAnalysisResult<Integer> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(0, result.getResult());
    }

    @Test
    public void testAmountSleepingSessionsNotEmptyList() {
        AmountSleepingSessions function = new AmountSleepingSessions();
        SleepAnalysisResult<Integer> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(13, result.getResult());
    }

    @Test
    public void testMinimumSleepingSessionEmptyList() {
        sleepingSessionList = new ArrayList<>();
        MinimumSleepingSession function = new MinimumSleepingSession();
        SleepAnalysisResult<Long> result = function.apply(sleepingSessionList);
        Assertions.assertNull(result);
    }

    @Test
    public void testMinimumSleepingSessionNotEmptyList() {
        MinimumSleepingSession function = new MinimumSleepingSession();
        SleepAnalysisResult<Long> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(45, result.getResult());
    }

    @Test
    public void testMaximumSleepingSessionEmptyList() {
        sleepingSessionList = new ArrayList<>();
        MaximumSleepingSession function = new MaximumSleepingSession();
        SleepAnalysisResult<Long> result = function.apply(sleepingSessionList);
        Assertions.assertNull(result);
    }

    @Test
    public void testMaximumSleepingSessionNotEmptyList() {
        MaximumSleepingSession function = new MaximumSleepingSession();
        SleepAnalysisResult<Long> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(500, result.getResult());
    }

    @Test
    public void testBadSleepingSessionCountEmptyList() {
        sleepingSessionList = new ArrayList<>();
        BadSleepingSessionCount function = new BadSleepingSessionCount();
        SleepAnalysisResult<Long> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(0, result.getResult());
    }

    @Test
    public void testBadSleepingSessionCountNotEmptyList() {
        BadSleepingSessionCount function = new BadSleepingSessionCount();
        SleepAnalysisResult<Long> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(2, result.getResult());
    }

    @Test
    public void testNoSleepNightCountEmptyList() {
        sleepingSessionList = new ArrayList<>();
        NoSleepNightCount function = new NoSleepNightCount();
        SleepAnalysisResult<Integer> result = function.apply(sleepingSessionList);
        Assertions.assertNull(result);
    }

    @Test
    public void testNoSleepNightCountNotEmptyList() {
        NoSleepNightCount function = new NoSleepNightCount();
        SleepAnalysisResult<Integer> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(20, result.getResult());
    }

    @Test
    public void testNoSleepNightCountBetweenMonths() {
        sleepingSessionList = List.of(new SleepingSession(LocalDateTime.of(2026,1,31,23,39), LocalDateTime.of(2026,2,1,3,40), SleepingQuality.GOOD));
        NoSleepNightCount function = new NoSleepNightCount();
        SleepAnalysisResult<Integer> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(0, result.getResult());
    }

    @Test
    public void testNoSleepNightCountBetweenOneNight() {
        sleepingSessionList = List.of(new SleepingSession(LocalDateTime.of(2026,2,1,1,39), LocalDateTime.of(2026,2,1,3,40), SleepingQuality.GOOD));
        NoSleepNightCount function = new NoSleepNightCount();
        SleepAnalysisResult<Integer> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(0, result.getResult());
    }

    @Test
    public void testNoSleepNightCountPreviousNightCount() {
        sleepingSessionList = List.of(new SleepingSession(LocalDateTime.of(2026,2,1,7,39), LocalDateTime.of(2026,2,1,8,40), SleepingQuality.GOOD));
        NoSleepNightCount function = new NoSleepNightCount();
        SleepAnalysisResult<Integer> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(1, result.getResult());
    }

    @Test
    public void testGetChronotypeEmptyList() {
        sleepingSessionList = new ArrayList<>();
        GetChronotype function = new GetChronotype();
        SleepAnalysisResult<Chronotypes> result = function.apply(sleepingSessionList);
        Assertions.assertNull(result);
    }

    @Test
    public void testGetChronotypeNotEmptyList() {
        GetChronotype function = new GetChronotype();
        SleepAnalysisResult<Chronotypes> result = function.apply(sleepingSessionList);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testGetChronotypeOwl() {
        sleepingSessionList = List.of(new SleepingSession(LocalDateTime.of(2026,2,1,0,39), LocalDateTime.of(2026,2,1,9,40), SleepingQuality.GOOD));
        GetChronotype function = new GetChronotype();
        SleepAnalysisResult<Chronotypes> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(Chronotypes.Сова, result.getResult());
    }

    @Test
    public void testGetChronotypeLark() {
        sleepingSessionList = List.of(new SleepingSession(LocalDateTime.of(2026,2,1,21,39), LocalDateTime.of(2026,2,2,6,40), SleepingQuality.GOOD));
        GetChronotype function = new GetChronotype();
        SleepAnalysisResult<Chronotypes> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(Chronotypes.Жаворонок, result.getResult());
    }

    @Test
    public void testGetChronotypePidgin() {
        GetChronotype function = new GetChronotype();
        SleepAnalysisResult<Chronotypes> result = function.apply(sleepingSessionList);
        Assertions.assertEquals(Chronotypes.Голубь, result.getResult());
    }
}