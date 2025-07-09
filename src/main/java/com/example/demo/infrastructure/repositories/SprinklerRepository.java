package com.example.demo.infrastructure.repositories;

import com.example.demo.domain.models.Sprinkler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprinklerRepository extends JpaRepository<Sprinkler, Long> {
}
