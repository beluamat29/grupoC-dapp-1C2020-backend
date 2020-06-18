package com.example.demo.model.store;

import com.example.demo.builders.StoreBuilder;
import com.example.demo.model.StoreSchedule;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreTurnsSystemTest {

    @Test
    public void elProximoTurnoDeUnNuevoComercioSeraElPrimerHorarioDisponiblePostAperturaQueEsteEnSuRangoHorario(){
        List<DayOfWeek> openingDays = Arrays.asList(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY );
        StoreSchedule schedule = new StoreSchedule(openingDays, LocalTime.of(10,0), LocalTime.of(12,30));
        LocalDateTime askingForTurnMoment = LocalDateTime.of(2020, 5, 6, 14, 5);
        LocalDate openingDate = askingForTurnMoment.toLocalDate();
        Store store = StoreBuilder.aStore().withStoreSchedule(schedule).withOpeningDate(openingDate).build();
        assertEquals(store.nextTurn(askingForTurnMoment), LocalDateTime.of(2020, 5, 7, 10,0));
    }

    @Test
    public void siPidoDosTurnosYHayDosDisponiblesEnElMismoDiaSeranOtorgadosParaElMismoDiaCon15MinDeDiferencia() {
        List<DayOfWeek> openingDays = Arrays.asList(DayOfWeek.WEDNESDAY);
        StoreSchedule schedule = new StoreSchedule(openingDays, LocalTime.of(10,0), LocalTime.of(12,30));
        LocalDateTime askingForTurnMoment = LocalDateTime.of(2020, 5, 6, 14, 5);
        LocalDate openingDate = askingForTurnMoment.toLocalDate();
        Store store = StoreBuilder.aStore().withStoreSchedule(schedule).withOpeningDate(openingDate).build();
        LocalDateTime firstGivenTurn = store.nextTurn(askingForTurnMoment);
        LocalDateTime secondGivenTurn = store.nextTurn(askingForTurnMoment);
        assertEquals(secondGivenTurn, firstGivenTurn.plusMinutes(15));
    }

    @Test
    public void siPidoDosTurnosYHayUnoSoloDisponibleParaEseDiaElSegundoTurnoTendraUnDiaDistinto() {
        List<DayOfWeek> openingDays = Arrays.asList(DayOfWeek.WEDNESDAY);
        StoreSchedule schedule = new StoreSchedule(openingDays, LocalTime.of(10,0), LocalTime.of(10, 15));
        LocalDateTime askingForTurnMoment = LocalDateTime.of(2020, 5, 6, 14, 5);
        LocalDate openingDate = askingForTurnMoment.toLocalDate();
        Store store = StoreBuilder.aStore().withStoreSchedule(schedule).withOpeningDate(openingDate).build();
        LocalDateTime firstGivenTurn = store.nextTurn(askingForTurnMoment);
        LocalDateTime secondGivenTurn = store.nextTurn(askingForTurnMoment);
        LocalDateTime thirdturn = store.nextTurn(askingForTurnMoment);
        assertEquals(secondGivenTurn, firstGivenTurn.plusMinutes(15));
    }
}
