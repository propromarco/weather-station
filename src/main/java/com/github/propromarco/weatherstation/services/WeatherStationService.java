package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.data.OwmClient;
import com.github.propromarco.weatherstation.data.WeatherForecastResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherStationService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private OwmClient client;
    private WeatherForecastResponse troisdorf, hadamar, koeln;

    @Scheduled(
            cron = "*/15 * * * * *"
    )
    public void loadWeatherData() throws IOException {
        log.info("Start loading WeatherData");
        this.troisdorf = client.forecastWeatherAtCity("Troisdorf");
        this.hadamar = client.forecastWeatherAtCity("Hadamar");
        this.koeln = client.forecastWeatherAtCity("Köln");
        log.info("End loading WeatherData");
    }

    public WeatherForecastResponse getTroisdorf() {
        return troisdorf;
    }

    public WeatherForecastResponse getHadamar() {
        return hadamar;
    }

    public WeatherForecastResponse getKoeln() {
        return koeln;
    }
}
