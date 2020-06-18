package com.example.demo.repositories;

import com.example.demo.model.StoreSchedule;
import com.example.demo.repositories.storeSchedule.StoreScheduleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StoreScheduleRepositoryTest{

    @Autowired
    private StoreScheduleRepository storeScheduleRepository;

    @Test
    public void aStoreScheduleIsRetrieved(){
        List<DayOfWeek> days = Arrays.asList(DayOfWeek.FRIDAY);
        StoreSchedule schedule = new StoreSchedule(days, LocalTime.of(9, 0), LocalTime.of(18,0));
        storeScheduleRepository.save(schedule);

        Optional<StoreSchedule> retrievedSchedule = storeScheduleRepository.findById(schedule.id());
        assertThat(retrievedSchedule).hasValue(schedule);
    }
}
