package com.github.propromarco.weatherstation.services;

public class Data {
    private final String name;
    private final String icon;
    private final String description;
    private final String temp;
    private final String tempMin;
    private final String tempMax;
    private final String sunrise;
    private final String sunset;

    public Data(String name, String icon, String description, String temp, String tempMin, String tempMax, String sunrise, String sunset) {
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getTemp() {
        return temp;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}
