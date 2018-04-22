package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.data.ForecastWeatherData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Helper {

    private final SimpleDateFormat dateFormatter;
    private final SimpleDateFormat timeFormatter;
    private final SimpleDateFormat jsonDateTimeFormatter;

    public Helper() {
        this.jsonDateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateFormatter = new SimpleDateFormat("EE, dd.MM.yyyy");
        this.timeFormatter = new SimpleDateFormat("HH:mm");
    }

    public String formatDate(String timeString) throws ParseException {
        Date date = jsonDateTimeFormatter.parse(timeString);
        String formatted = dateFormatter.format(date);
        return formatted;
    }

    public String formatTime(String timeString) throws ParseException {
        Date date = jsonDateTimeFormatter.parse(timeString);
        String formatted = timeFormatter.format(date);
        return formatted;
    }

    public String formatTemp(float temp) {
        return Math.round(temp) + "°C";
    }

    public List<String> getDates(List<ForecastWeatherData> data) throws ParseException {
        List<String> list = new ArrayList<String>();
        for (ForecastWeatherData forecastWeatherData : data) {
            String s = formatDate(forecastWeatherData.getDateTimeString());
            if (!list.contains(s)) {
                list.add(s);
            }
        }
        return list;
    }

    public int getColspan(List<ForecastWeatherData> data, String aktual) throws ParseException {
        int colspan = 0;
        for (ForecastWeatherData forecastWeatherData : data) {
            String s = formatDate(forecastWeatherData.getDateTimeString());
            if (s.equalsIgnoreCase(aktual)) {
                colspan++;
            }
        }
        return colspan;
    }
}
