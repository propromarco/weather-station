package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jabx.Current;
import com.github.propromarco.weatherstation.jabx.Forecast;
import com.github.propromarco.weatherstation.jabx.ForecastEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherStationService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Helper helper;

    @Autowired
    private OpenweathermapService openweathermapService;

    private Forecast troisdorfForecast, hadamarForecast, koelnForecast;
    private Current troisdorfCurrent, hadamarCurrent, koelnCurrent;

    public WeatherStationService() {
        this.helper = new Helper();
    }

    @PostConstruct
    public void init() throws IOException, ParseException {
        loadWeatherData();
    }

    @Scheduled(
            cron = "0 */5 * * * *"
    )
    public void loadWeatherData() throws IOException, ParseException {
        log.info("Start loading WeatherData");
        this.troisdorfForecast = openweathermapService.getForecast("Troisdorf");
        this.troisdorfCurrent = openweathermapService.getCurrect("Troisdorf");
        recreate(this.troisdorfForecast);
        this.hadamarForecast = openweathermapService.getForecast("Hadamar");
        this.hadamarCurrent = openweathermapService.getCurrect("Hadamar");
        recreate(this.hadamarForecast);
        this.koelnForecast = openweathermapService.getForecast("Köln");
        this.koelnCurrent = openweathermapService.getCurrect("Köln");
        recreate(this.koelnForecast);
        log.info("End loading WeatherData");
    }

    private void recreate(Forecast response) throws ParseException {
        List<ForecastEntry> forecasts = response.getList();
        List<ForecastEntry> recreated = new ArrayList<ForecastEntry>();
        int count = 0;
        for (ForecastEntry forecast : forecasts) {
            long date = forecast.getDt();
            String s = helper.formatTime(date);
            if ("20:00".equals(s) || "23:00".equals(s) || "02:00".equals(s)) {
                continue;
            } else if (count >= 12) {
                continue;
            }
            recreated.add(forecast);
            count++;
        }
        forecasts.clear();
        forecasts.addAll(recreated);
    }

    public Forecast getTroisdorfForecast() {
        return troisdorfForecast;
    }

    public Forecast getHadamarForecast() {
        return hadamarForecast;
    }

    public Forecast getKoelnForecast() {
        return koelnForecast;
    }

    public Current getTroisdorfCurrent() {
        return troisdorfCurrent;
    }

    public Current getHadamarCurrent() {
        return hadamarCurrent;
    }

    public Current getKoelnCurrent() {
        return koelnCurrent;
    }
}
