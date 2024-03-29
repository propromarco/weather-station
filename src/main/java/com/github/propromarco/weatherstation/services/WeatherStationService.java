package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jaxb.Current;
import com.github.propromarco.weatherstation.jaxb.Forecast;
import com.github.propromarco.weatherstation.jaxb.ForecastEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.*;

@Service
public class WeatherStationService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Helper helper;
    private final GregorianCalendar calendar;

    @Autowired
    private OpenweathermapService openweathermapService;

    @Value("${cities}")
    private String[] cities;

    private final List<CurrentWithForecast> all = new ArrayList<>();

    public WeatherStationService() {
        this.helper = new Helper();
        this.calendar = new GregorianCalendar();
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
            String[] cityAndCountry = city.split("_");
            Forecast forecast = openweathermapService.getForecast(cityAndCountry);
            Current current = openweathermapService.getCurrect(cityAndCountry);
            recreate(forecast, current);
            all.add(new CurrentWithForecast(current, forecast));
        }
        log.info("End loading WeatherData");
    }

    private void recreate(Forecast response, Current current) {
        List<ForecastEntry> forecasts = response.getList();
        List<ForecastEntry> recreated = new ArrayList<ForecastEntry>();
        int count = 0;
        for (ForecastEntry forecast : forecasts) {
            float tempMin = forecast.getMain().getTemp_min();
            float tempMax = forecast.getMain().getTemp_max();
            long date = forecast.getDt();
            String forcastDate = helper.formatDate(date);
            long currentDt = current.getDt();
            String currentDate = helper.formatDate(currentDt);
            if (currentDate.equalsIgnoreCase(forcastDate)) {
                float min = current.getMain().getTemp_min();
                float max = current.getMain().getTemp_max();
                min = Math.min(min, tempMin);
                max = Math.max(max, tempMax);
                current.getMain().setTemp_min(min);
                current.getMain().setTemp_max(max);
            }
            Date d = Date.from(Instant.ofEpochSecond(date));
            calendar.setTime(d);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            if (hours < 4 || hours > 18) {
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
