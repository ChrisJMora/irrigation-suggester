package com.example.demo.infrastructure.repositories;

import com.example.demo.domain.models.Crop;
import com.example.demo.domain.models.Schedule;
import com.example.demo.domain.models.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByCropAndStatusAndDate(Crop crop,
                                              ScheduleStatus status,
                                              LocalDate date);
    List<Schedule> findByCrop(Crop crop);
}
