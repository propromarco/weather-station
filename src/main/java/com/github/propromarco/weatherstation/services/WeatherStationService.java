package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jabx.Current;
import com.github.propromarco.weatherstation.jabx.Forecast;
import com.github.propromarco.weatherstation.jabx.ForecastEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherStationService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Helper helper;

    @Autowired
    private OpenweathermapService openweathermapService;

    @Value("${cities}")
    private String[] cities;

    private final List<CurrentWithForecast> all = new ArrayList<>();

    public WeatherStationService() {
        this.helper = new Helper();
    }

    @PostConstruct
    public void init() {
        loadWeatherData();
    }

    @Scheduled(
            cron = "0 */5 * * * *"
    )
    public void loadWeatherData() {
        log.info("Start loading WeatherData");
        all.clear();
        for (String city : cities) {
            Forecast forecast = openweathermapService.getForecast(city);
            Current current = openweathermapService.getCurrect(city);
            recreate(forecast);
            all.add(new CurrentWithForecast(current, forecast));
        }
        log.info("End loading WeatherData");
    }

    private void recreate(Forecast response) {
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

    public List<CurrentWithForecast> getAll() {
        return all;
    }
}
