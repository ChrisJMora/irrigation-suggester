/**
 * Implementation service for managing locations.
 * This service provides methods to perform CRUD operations on locations in the database.
 */
package com.example.demo.application.services;

import com.example.demo.domain.exceptions.EmptyTableException;
import com.example.demo.domain.exceptions.SaveRecordFailException;
import com.example.demo.domain.models.Location;
import com.example.demo.infrastructure.repositories.LocationRepository;
import com.example.demo.application.ports.services.LocationService;
import com.example.demo.application.ports.mappers.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LocationServiceImp implements LocationService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationMapper locationMapper;

    private final Random random = new Random();

    /**
     * Retrieves all locations from the database.
     * If the table is empty, an exception is thrown.
     *
     * @return A list of locations found in the database.
     * @throws EmptyTableException When the locations table has no records.
     */
    @Override
    public List<Location> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        if (locations.isEmpty()) throw new EmptyTableException(Location.class);
        return locations;
    }

    /**
     * Retrieves a random location from the database.
     * If the table is empty, an exception is thrown.
     *
     * @return A random location from the database.
     * @throws EmptyTableException When the locations table has no records.
     */
    @Override
    public Location getRandomLocation() {
        List<Location> locations = getAllLocations();
        int randomIndex = random.nextInt(locations.size());
        return locations.get(randomIndex);
    }

    /**
     * Creates a random location with random latitude and longitude values.
     * The new location is then saved to the database.
     *
     * @return The newly created random location.
     * @throws SaveRecordFailException When the location could not be saved.
     */
    @Override
    public Location createRandomLocation() {
        Location location = new Location();
        float latitude = -90 + random.nextFloat() * 180;
        location.setLatitude(latitude);
        float longitude = -180 + random.nextFloat() * 360;
        location.setLongitude(longitude);
        return saveLocation(location);
    }

    /**
     * Saves a location to the database.
     * If the location is not saved successfully, an exception is thrown.
     *
     * @param location The location to be saved.
     * @return The saved location.
     * @throws SaveRecordFailException When the location could not be saved.
     */
    @Override
    public Location saveLocation(Location location) {
        Location savedLocation = locationRepository.save(location);
        if (savedLocation.getId() == null) throw new SaveRecordFailException(Location.class);
        return savedLocation;
    }
}