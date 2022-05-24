package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jaxb.Current;
import com.github.propromarco.weatherstation.jaxb.Main;
import com.github.propromarco.weatherstation.jaxb.Sys;
import com.github.propromarco.weatherstation.jaxb.Weather;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
public class WeatherStationRestController {

    private final WeatherStationService weatherStationService;
    private final Helper helper = new Helper();

    public WeatherStationRestController(WeatherStationService weatherStationService) {
        this.weatherStationService = weatherStationService;
    }

    @GetMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    public Data getData() {
        List<CurrentWithForecast> all = weatherStationService.getAll();
        int minutes = new GregorianCalendar().get(Calendar.MINUTE);
        int position = minutes % all.size();
        Current current = all.get(position).getCurrent();
        return map(current);
    }

    private Data map(Current current) {
        String name = current.getName();
        Weather weather = current.getWeather()[0];
        String description = weather.getDescription();
        String icon = weather.getIcon();
        Main main = current.getMain();
        String temp = toTemp(main.getTemp());
        String temp_min = toTemp(main.getTemp_min());
        String temp_max = toTemp(main.getTemp_max());
        Sys sys = current.getSys();
        String sunrise = toDate(sys.getSunrise());
        String sunset = toDate(sys.getSunset());
        return new Data(name, icon, description, temp, temp_min, temp_max, sunrise, sunset);
    }

    private String toTemp(float temp) {
        return helper.formatTemp(temp);
    }

    private String toDate(long l) {
        return helper.formatTime(l);
    }

}
