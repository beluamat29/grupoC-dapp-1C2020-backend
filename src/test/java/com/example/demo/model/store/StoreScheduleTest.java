package com.example.demo.model.store;

import com.example.demo.model.StoreSchedule;
import com.example.demo.model.exceptions.WrongScheduleException;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StoreScheduleTest {

    @Test
    public void aStoreScheduleKnowsIfItsOpenAtAnSpecificDay(){
        List<DayOfWeek> openingDays = Arrays.asList(DayOfWeek.FRIDAY);
        StoreSchedule storeSchedule = new StoreSchedule(openingDays, LocalTime.of(9, 0), LocalTime.of(19, 0));
        assertTrue(storeSchedule.availableOnDay(DayOfWeek.FRIDAY));
    }

    @Test
    public void unRangoHorarioDeComercioEstaDisponibleEnUnDiaYHorario(){
        List<DayOfWeek> openingDays = Arrays.asList(DayOfWeek.FRIDAY);
        StoreSchedule horarioComercio = new StoreSchedule(openingDays, LocalTime.of(9, 0), LocalTime.of(19, 0));
        assertTrue(horarioComercio.isAvailableOn(DayOfWeek.FRIDAY, LocalTime.of(11,0)));
    }

    @Test
    public void unRangoHorarioDeComercioNoPuedeTenerUnHorarioDeAperturaPosteriorAlHorarioDeCierre() {
        List<DayOfWeek> openingDays = Arrays.asList(DayOfWeek.FRIDAY);
        assertThrows(WrongScheduleException.class, () -> {    new StoreSchedule(openingDays, LocalTime.of(19, 0), LocalTime.of(9, 0));     });
    }
}
