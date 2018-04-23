package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.data.ForecastWeatherData;
import com.github.propromarco.weatherstation.data.OwmClient;
import com.github.propromarco.weatherstation.data.WeatherForecastResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherStationService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Helper helper;

    @Autowired
    private OwmClient client;
    private WeatherForecastResponse troisdorf, hadamar, koeln;

    public WeatherStationService() {
        this.helper = new Helper();
    }

    @Scheduled(
            cron = "*/15 * * * * *"
    )
    public void loadWeatherData() throws IOException, ParseException {
        log.info("Start loading WeatherData");
        this.troisdorf = client.forecastWeatherAtCity("Troisdorf");
        recreate(this.troisdorf);
        this.hadamar = client.forecastWeatherAtCity("Hadamar");
        recreate(this.hadamar);
        this.koeln = client.forecastWeatherAtCity("Köln");
        recreate(this.koeln);
        log.info("End loading WeatherData");
    }

    private void recreate(WeatherForecastResponse response) throws ParseException {
        List<ForecastWeatherData> forecasts = response.getForecasts();
        List<ForecastWeatherData> recreated = new ArrayList<ForecastWeatherData>();
        int count = 0;
        for (ForecastWeatherData forecast : forecasts) {
            String dateTimeString = forecast.getDateTimeString();
            String s = helper.formatTime(dateTimeString);
            if ("21:00".equals(s) || "00:00".equals(s) || "03:00".equals(s)) {
                continue;
            } else if (count >= 20) {
                continue;
            }
            recreated.add(forecast);
            count++;
        }
        forecasts.clear();
        forecasts.addAll(recreated);
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
