/**
 * Implementation service for managing soil data.
 * This service provides methods to perform operations related to soils in the database.
 */
package com.example.demo.application.services;

import com.example.demo.domain.exceptions.EmptyTableException;
import com.example.demo.domain.models.Soil;
import com.example.demo.infrastructure.repositories.SoilRepository;
import com.example.demo.application.ports.services.SoilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SoilServiceImp implements SoilService {

    @Autowired
    private SoilRepository soilRepository;

    private final Random random = new Random();

    /**
     * Retrieves all soils from the database.
     * If the table is empty, an exception is thrown.
     *
     * @return A list of soils found in the database.
     * @throws EmptyTableException When the soils table has no records.
     */
    @Override
    public List<Soil> getAllSoils() {
        List<Soil> soils = soilRepository.findAll();
        if (soils.isEmpty()) throw new EmptyTableException(Soil.class);
        return soils;
    }

    /**
     * Retrieves a random soil from the database.
     * If the table is empty, an exception is thrown.
     *
     * @return A random soil from the database.
     * @throws EmptyTableException When the soils table has no records.
     */
    @Override
    public Soil getRandomSoil() {
        List<Soil> soils = getAllSoils();
        int randomIndex = random.nextInt(soils.size());
        return soils.get(randomIndex);
    }
}