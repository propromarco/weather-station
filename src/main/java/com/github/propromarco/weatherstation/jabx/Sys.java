package com.github.propromarco.weatherstation.jabx;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

public class Sys {

    private float type;
    private float id;
    private float message;
    private String country;
    private Date sunrise;
    private Date sunset;

    public float getMessage() {
        return message;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    @JsonDeserialize(converter = DateConverter.class)
    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }

    @JsonDeserialize(converter = DateConverter.class)
    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    public float getType() {
        return type;
    }

    public void setType(float type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", id = " + id + ", sunset = " + sunset + ", sunrise = " + sunrise + ", type = " + type + ", country = " + country + "]";
    }
}
