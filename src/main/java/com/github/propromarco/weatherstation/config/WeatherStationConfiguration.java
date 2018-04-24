package com.github.propromarco.weatherstation.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

@Configuration
public class WeatherStationConfiguration {

    @Value("${appid}")
    private String appid;

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    public RestTemplate createTemplate() {
        CloseableHttpClient defaultHttpClient = HttpClientBuilder
                .create()
                .setDefaultHeaders(createDefaultHeader())
                .build();
        HttpComponentsClientHttpRequestFactory redirectsEnabledRequestFactory = new HttpComponentsClientHttpRequestFactory(defaultHttpClient);
        RestTemplate restTemplate = new RestTemplate(redirectsEnabledRequestFactory);
        return restTemplate;
    }

    protected List<BasicHeader> createDefaultHeader() {
        BasicHeader acceptCharsetHeader = new BasicHeader("x-api-key", appid);
        return Arrays.asList(acceptCharsetHeader);
    }


}
