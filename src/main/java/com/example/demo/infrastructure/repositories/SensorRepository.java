package com.example.demo.infrastructure.repositories;

import com.example.demo.domain.models.Crop;
import com.example.demo.domain.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByCrop(Crop crop);
}
