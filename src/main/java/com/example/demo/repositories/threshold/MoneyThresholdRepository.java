package com.example.demo.repositories.threshold;

import com.example.demo.model.thresholds.MoneyThreshold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyThresholdRepository extends JpaRepository<MoneyThreshold, Long> {
}
