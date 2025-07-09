package com.example.demo.infrastructure.repositories;

import com.example.demo.domain.models.Soil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoilRepository extends JpaRepository<Soil, Long> {
}
