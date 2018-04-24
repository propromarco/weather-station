package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jabx.Current;
import com.github.propromarco.weatherstation.jabx.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenweathermapService {

    @Autowired
    private RestTemplate restTemplate;

    public Current getCurrect(String city) {
        ResponseEntity<Current> entity = restTemplate.getForEntity("http://api.openweathermap.org/data/2.5/weather?q=" + city + ",de&lang=de&units=metric", Current.class);
        Current current = entity.getBody();
        return current;
    }

    public Forecast getForecast(String city) {
        ResponseEntity<Forecast> entity = restTemplate.getForEntity("http://api.openweathermap.org/data/2.5/forecast?q=" + city + ",de&lang=de&units=metric", Forecast.class);
        Forecast current = entity.getBody();
        return current;
    }

}
