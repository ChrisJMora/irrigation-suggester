package com.example.demo.infrastructure.repositories;

import com.example.demo.domain.models.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    Optional<Forecast> findByDate(LocalDate date);
}
