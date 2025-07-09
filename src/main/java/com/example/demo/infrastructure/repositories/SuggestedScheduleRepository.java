package com.example.demo.infrastructure.repositories;

import com.example.demo.domain.models.Crop;
import com.example.demo.domain.models.SuggestedSchedule;
import com.example.demo.domain.models.SuggestedScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestedScheduleRepository extends JpaRepository<SuggestedSchedule, Long> {
    List<SuggestedSchedule> findByStatus(SuggestedScheduleStatus status);
    List<SuggestedSchedule> findByStatusAndCrop(SuggestedScheduleStatus status, Crop crop);
}
