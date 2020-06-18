package com.example.demo.model.turnsSystem;

import com.example.demo.model.StoreSchedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TurnsSystem {

    public static LocalDateTime primerTurnoDeLocal(LocalDate openingTime, StoreSchedule storeSchedule) {
        StoreSchedule storeTime = storeSchedule;
        List<LocalDate> openingDay = storeTime.days().stream().map(day -> nextDateWithDat(day, openingTime)).collect(Collectors.toList());
        LocalDate closestDayFromNow = openingDay.stream().min(Comparator.comparingLong(x -> ChronoUnit.DAYS.between(openingTime, x))).get();
        return generateFirstTurn(storeSchedule, closestDayFromNow);
    }

    private static LocalDateTime generateFirstTurn(StoreSchedule storeSchedule, LocalDate closestDayFromNow) {
        return LocalDateTime.of(closestDayFromNow.getYear(), closestDayFromNow.getMonth(), closestDayFromNow.getDayOfMonth(), storeSchedule.openingTime().getHour(), storeSchedule.openingTime().getMinute());
    }

   /* private static LocalDateTime encontrarHorarioApertura(LocalDate day, StoreSchedule storeSchedule) {
        StoreSchedule storeTime = storeSchedule;
        storeTime = storeTime.stream().filter(schedule -> schedule.availableOnDay().equals(day.getDayOfWeek())).collect(Collectors.toList());
        StoreSchedule horario = storeTime.stream().min(Comparator.comparing(StoreSchedule::openingTime)).get();
        return LocalDateTime.of(day.getYear(),day.getMonth(), day.getDayOfMonth(), horario.openingTime().getHour(), horario.openingTime().getMinute());
    }*/


    private static LocalDate nextDateWithDat(DayOfWeek dayOfWeek, LocalDate fromNowOn) {
        LocalDate now = fromNowOn;
        return now.with(TemporalAdjusters.next(dayOfWeek));
    }
}
