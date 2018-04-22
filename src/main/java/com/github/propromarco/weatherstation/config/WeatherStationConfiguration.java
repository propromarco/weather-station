package com.github.propromarco.weatherstation.config;

import com.github.propromarco.weatherstation.data.OwmClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
public class WeatherStationConfiguration {

    @Value("${appid}")
    private String appid;

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    public OwmClient ownClient(){
        OwmClient owmClient = new OwmClient();
        owmClient.setAPPID(appid);
        return owmClient;
    }

}
