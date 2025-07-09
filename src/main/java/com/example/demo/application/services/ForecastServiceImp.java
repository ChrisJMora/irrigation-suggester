/**
 * Implementation service for managing weather forecasts.
 * This service provides methods to perform CRUD operations on forecasts in the database.
 */
package com.example.demo.application.services;

import com.example.demo.domain.exceptions.EmptyRecordException;
import com.example.demo.domain.exceptions.EmptyTableException;
import com.example.demo.domain.exceptions.SaveRecordFailException;
import com.example.demo.domain.models.Forecast;
import com.example.demo.infrastructure.repositories.ForecastRepository;
import com.example.demo.application.ports.services.ForecastService;
import com.example.demo.application.ports.services.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ForecastServiceImp implements ForecastService {

    @Autowired
    private ForecastRepository forecastRepository;
    @Autowired
    private LocationService locationService;

    /**
     * Retrieves all forecasts from the database.
     * If the table is empty, an exception is thrown.
     *
     * @return A list of forecasts found in the database.
     * @throws EmptyTableException When the forecasts table has no records.
     */
    @Override
    public List<Forecast> getAllForecasts() {
        List<Forecast> forecasts = forecastRepository.findAll();
        if (forecasts.isEmpty()) throw new EmptyTableException(Forecast.class);
        return forecasts;
    }

    /**
     * Retrieves the forecast for today.
     * If there is no forecast for today, an exception is thrown.
     *
     * @return The forecast for today's date.
     * @throws EmptyRecordException When no forecast is found for today.
     */
    @Override
    public Forecast getForecastFromToday() {
        LocalDate today = LocalDate.now();
        Optional<Forecast> todayForecast = forecastRepository.findByDate(today);
        if (todayForecast.isEmpty()) throw new EmptyRecordException(Forecast.class);
        return todayForecast.get();
    }

    /**
     * Creates or updates a forecast in the database.
     * If the forecast is not saved successfully, an exception is thrown.
     *
     * @param forecast The forecast to be saved.
     * @return The saved forecast.
     * @throws SaveRecordFailException When the forecast could not be saved.
     */
    @Override
    public Forecast saveForecast(Forecast forecast) {
        Forecast savedForecast = forecastRepository.save(forecast);
        if (savedForecast.getId() == null) throw new SaveRecordFailException(Forecast.class);
        return savedForecast;
    }

    private Forecast createForecastForToday() {
        log.info("Creating a new forecast for today.");
        Forecast todayForecast = new Forecast(locationService.getRandomLocation());
        Forecast savedForecast = saveForecast(todayForecast);
        log.info("New forecast created and saved: {}", savedForecast);
        return savedForecast;
    }

    @Override
    public Forecast getOrCreateTodayForecast() {
        Forecast todayForecast;
        try {
            todayForecast = getForecastFromToday();
            log.info("Retrieved today's forecast: {}", todayForecast);
        } catch (EmptyRecordException ex) {
            log.info("There is no forecast for today. Creating a new one.");
            todayForecast = createForecastForToday();
        }
        return todayForecast;
    }
}