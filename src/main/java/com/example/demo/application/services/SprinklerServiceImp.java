/**
 * Implementation service for managing sprinklers.
 * This service provides methods to perform operations related to sprinklers in the database.
 */
package com.example.demo.application.services;

import com.example.demo.domain.exceptions.EmptyTableException;
import com.example.demo.domain.exceptions.SaveRecordFailException;
import com.example.demo.domain.models.Crop;
import com.example.demo.domain.models.IrrigationType;
import com.example.demo.domain.models.Sprinkler;
import com.example.demo.domain.utils.factories.SprinklerFactory;
import com.example.demo.infrastructure.repositories.SprinklerRepository;
import com.example.demo.application.ports.services.SprinklerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SprinklerServiceImp implements SprinklerService {

    private final SprinklerFactory factory;
    private final SprinklerRepository repository;

    @Autowired
    public SprinklerServiceImp(SprinklerFactory factory, SprinklerRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    /**
     * Retrieves all sprinklers from the database.
     * If the table is empty, an exception is thrown.
     *
     * @return A list of sprinklers found in the database.
     * @throws EmptyTableException When the sprinklers table has no records.
     */
    @Override
    public List<Sprinkler> getAllSprinklers() {
        List<Sprinkler> sprinklers = repository.findAll();
        if (sprinklers.isEmpty()) throw new EmptyTableException(Sprinkler.class);
        return sprinklers;
    }

    /**
     * Creates a specified number of sprinklers associated with a given crop and irrigation type.
     *
     * @param crop The crop to associate with the new sprinklers.
     * @param quantity The number of sprinklers to create.
     * @param irrigationType The type of irrigation to be used by the sprinklers.
     * @return A list of the newly created sprinklers.
     */
    @Override
    public List<Sprinkler> createSprinklers(Crop crop, int quantity, IrrigationType irrigationType) {
        List<Sprinkler> sprinklers = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            sprinklers.add(factory.create(crop, irrigationType));
        }
        return saveSprinklers(sprinklers);
    }

    /**
     * Saves a list of sprinklers in the database.
     * If any sprinkler in the list is not saved successfully, an exception is thrown.
     *
     * @param sprinklers The list of sprinklers to be saved.
     * @return A list of the saved sprinklers.
     * @throws SaveRecordFailException When any sprinkler could not be saved.
     */
    @Override
    public List<Sprinkler> saveSprinklers(List<Sprinkler> sprinklers) {
        List<Sprinkler> savedSprinklers = repository.saveAll(sprinklers);
        for (Sprinkler sprinkler : savedSprinklers) {
            if (sprinkler.getId() == null) {
                throw new SaveRecordFailException(Sprinkler.class);
            }
        }
        return savedSprinklers;
    }
}