package com.antocecere77.redisspring.city.controller;

import com.antocecere77.redisspring.city.dto.City;
import com.antocecere77.redisspring.city.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("{zipCode}")
    public Mono<City> getCity(@PathVariable String zipCode) {
        return cityService.getCity(zipCode);
    }
}
