package com.antocecere77.redisspring.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final ExternalServiceClient externalServiceClient;

    @Cacheable("weather")
    public int getInfo(int zip) {
        return 0;
    }

    @Scheduled(fixedRate = 10_000)
    public void update() {
        System.out.println("Updating weather");
        IntStream.rangeClosed(1, 5)
                .forEach(this.externalServiceClient::getWeatherInfo);
    }
}
