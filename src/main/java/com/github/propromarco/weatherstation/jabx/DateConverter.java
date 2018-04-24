package com.github.propromarco.weatherstation.jabx;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

import java.time.Instant;
import java.util.Date;

public class DateConverter implements Converter<Long, Date> {

    @Override
    public Date convert(Long value) {
        Date date = Date.from(Instant.ofEpochSecond(value));
        return date;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        JavaType javaType = typeFactory.constructType(Long.class);
        return javaType;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        JavaType javaType = typeFactory.constructType(Date.class);
        return javaType;
    }
}
