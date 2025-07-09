/**
 * Implementation service for managing sensors and their records.
 * This service provides methods to perform CRUD operations on sensors and sensor records in the database.
 */
package com.example.demo.application.services;

import com.example.demo.domain.exceptions.EmptyFilterException;
import com.example.demo.domain.exceptions.EmptyRecordException;
import com.example.demo.domain.exceptions.EmptyTableException;
import com.example.demo.domain.exceptions.SaveRecordFailException;
import com.example.demo.domain.models.Crop;
import com.example.demo.domain.models.Sensor;
import com.example.demo.domain.models.SensorRecord;
import com.example.demo.infrastructure.repositories.SensorRecordRepository;
import com.example.demo.infrastructure.repositories.SensorRepository;
import com.example.demo.application.ports.services.SensorService;
import com.example.demo.application.ports.NormalDistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SensorServiceImp implements SensorService {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private SensorRecordRepository sensorRecordRepository;
    @Autowired
    private NormalDistributionService normalDistributionService;

    /**
     * Retrieves all sensors from the database.
     * If the table is empty, an exception is thrown.
     *
     * @return A list of sensors found in the database.
     * @throws EmptyTableException When the sensors table has no records.
     */
    @Override
    public List<Sensor> getAllSensors() {
        List<Sensor> sensors = sensorRepository.findAll();
        if (sensors.isEmpty()) throw new EmptyTableException(Sensor.class);
        return sensors;
    }

    /**
     * Retrieves all sensors filtered by crop.
     * If no sensors match the criteria, an exception is thrown.
     *
     * @param crop The crop to filter sensors by.
     * @return A list of sensors filtered by the specified crop.
     * @throws EmptyFilterException When no sensors match the provided crop.
     */
    @Override
    public List<Sensor> getAllSensorsByCrop(Crop crop) {
        List<Sensor> sensors = sensorRepository.findByCrop(crop);
        if (sensors.isEmpty()) throw new EmptyFilterException(Sensor.class, Crop.class);
        return sensors;
    }

    /**
     * Retrieves all sensor records from the database.
     * If the table is empty, an exception is thrown.
     *
     * @return A list of all sensor records found in the database.
     * @throws EmptyTableException When the sensor records table has no records.
     */
    @Override
    public List<SensorRecord> getAllRecords() {
        List<SensorRecord> records = sensorRecordRepository.findAll();
        if (records.isEmpty()) throw new EmptyTableException(SensorRecord.class);
        return records;
    }

    /**
     * Retrieves the most recent record of a specified sensor.
     * If no record is found, an exception is thrown.
     *
     * @param sensor The sensor for which to retrieve the most recent record.
     * @return The most recent record of the specified sensor.
     * @throws EmptyRecordException When no record is found for the specified sensor.
     */
    @Override
    public SensorRecord getMostRecentRecord(Sensor sensor) {
        Optional<SensorRecord> recentRecord = sensorRecordRepository.findMostRecentRecordBySensor(sensor);
        if (recentRecord.isEmpty()) throw new EmptyRecordException(SensorRecord.class);
        return recentRecord.get();
    }

    /**
     * Creates or updates a sensor record in the database.
     * If the record is not saved successfully, an exception is thrown.
     *
     * @param sensorRecord The sensor record to be saved.
     * @return The saved sensor record.
     * @throws SaveRecordFailException When the sensor record could not be saved.
     */
    @Override
    public SensorRecord saveSensorRecord(SensorRecord sensorRecord) {
        SensorRecord savedRecord = sensorRecordRepository.save(sensorRecord);
        if (savedRecord.getId() == null) throw new SaveRecordFailException(SensorRecord.class);
        return savedRecord;
    }

    /**
     * Creates or updates a sensor in the database . If the sensor is not saved successfully, an exception is thrown.

     * @param sensor The sensor to be saved.
     * @return The saved sensor.
     * @throws SaveRecordFailException When the sensor could not be saved.
     */
    @Override
    public Sensor saveSensor(Sensor sensor) {
        Sensor savedSensor = sensorRepository.save(sensor);
        if (savedSensor.getId() == null) throw new SaveRecordFailException(Sensor.class);
        return savedSensor;
    }

    /**
     * Saves a list of sensors in the database.
     * If any sensor in the list is not saved successfully, an exception is thrown.
     *
     * @param sensors The list of sensors to be saved.
     * @return A list of the saved sensors.
     * @throws SaveRecordFailException When any sensor could not be saved.
     */
    @Override
    public List<Sensor> saveSensors(List<Sensor> sensors) {
        List<Sensor> savedSensors = sensorRepository.saveAll(sensors);
        for (Sensor sensor : savedSensors) {
            if (sensor.getId() == null) {
                throw new SaveRecordFailException(Sensor.class);
            }
        }
        return savedSensors;
    }

    /**
     * Creates a specified number of sensors associated with a given crop.
     *
     * @param crop The crop to associate with the new sensors.
     * @param quantity The number of sensors to create.
     * @return A list of the newly created sensors.
     */
    @Override
    public List<Sensor> createSensors(Crop crop, int quantity) {
        List<Sensor> sensors = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Sensor sensor = new Sensor();
            sensor.setCrop(crop);
            Sensor savedSensor = saveSensor(sensor);
            createRandomRecord(sensor);
            sensors.add(savedSensor);
        }
        return sensors;
    }

    /**
     * Creates a random sensor record for a specified sensor.
     * The record is generated using a normal distribution for humidity.
     *
     * @param sensor The sensor for which to create a random record.
     * @return The created random sensor record.
     */
    @Override
    public SensorRecord createRandomRecord(Sensor sensor) {
        SensorRecord randomRecord = new SensorRecord();
        randomRecord.setHumidity(normalDistributionService.generateNormal(0.6f, 0.15f));
        randomRecord.setDate(LocalDate.now());
        randomRecord.setTime(LocalTime.now());
        randomRecord.setSensor(sensor);
        return saveSensorRecord(randomRecord);
    }
}