package com.example.demo.infrastructure.repositories;

import com.example.demo.domain.models.Crop;
import com.example.demo.domain.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    Optional<Crop> findByLocation(Location location);
}
