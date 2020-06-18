package com.example.demo.repositories.storeSchedule;

import com.example.demo.model.StoreSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreScheduleRepository extends JpaRepository<StoreSchedule, Long> {
}
