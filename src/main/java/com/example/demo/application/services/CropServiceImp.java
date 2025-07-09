/**
 * Implementation service for managing crops.
 * This service provides methods to perform CRUD operations on crops in the database.
 */
package com.example.demo.application.services;

import com.example.demo.domain.exceptions.EmptyRecordException;
import com.example.demo.domain.exceptions.EmptyTableException;
import com.example.demo.domain.exceptions.SaveRecordFailException;
import com.example.demo.domain.models.Crop;
import com.example.demo.domain.models.Location;
import com.example.demo.infrastructure.repositories.CropRepository;
import com.example.demo.application.ports.services.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CropServiceImp implements CropService {

    @Autowired
    private CropRepository cropRepository;

    /**
     * Retrieves all crops from the database.
     * If the table is empty, an exception is thrown.
     *
     * @return A list of crops found in the database.
     * @throws EmptyTableException When the table has no records.
     */
    @Override
    public List<Crop> getAllCrops() {
        List<Crop> crops = cropRepository.findAll();
        if (crops.isEmpty()) throw new EmptyTableException(Crop.class);
        return crops;
    }

    /**
     * Retrieves a crop by its ID.
     *
     * @param id The ID of the crop to retrieve.
     * @return Crop The crop found.
     * @throws EmptyRecordException When no crop is found with the provided ID.
     */
    @Override
    public Crop getCropById(Long id) {
        Optional<Crop> crop = cropRepository.findById(id);
        if (crop.isEmpty()) throw new EmptyRecordException(Crop.class);
        return crop.get();
    }

    /**
     * Retrieves a crop by its location.
     *
     * @param location The location of the crop to retrieve.
     * @return Crop The crop found.
     * @throws EmptyRecordException When no crop is found at the provided location.
     */
    @Override
    public Crop getCropByLocation(Location location) {
        Optional<Crop> crop = cropRepository.findByLocation(location);
        if (crop.isEmpty()) throw new EmptyRecordException(Crop.class);
        return crop.get();
    }

    /**
     * Creates or updates a crop in the database.
     * If the crop is not saved successfully, an exception is thrown.
     *
     * @param crop The crop to be saved.
     * @return Crop The saved crop.
     * @throws SaveRecordFailException When the record could not be saved.
     */
    @Override
    public Crop saveCrop(Crop crop) {
        Crop savedCrop = cropRepository.save(crop);
        if (savedCrop.getId() == null) throw new SaveRecordFailException(Crop.class);
        return savedCrop;
    }
}