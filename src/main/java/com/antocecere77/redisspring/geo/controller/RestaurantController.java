package com.antocecere77.redisspring.geo.controller;

import com.antocecere77.redisspring.geo.dto.Restaurant;
import com.antocecere77.redisspring.geo.service.RestaurantLocatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("geo")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantLocatorService locatorService;

    @GetMapping("{zip}")
    public Flux<Restaurant> getRestaurants(@PathVariable String zip) {
        return this.locatorService.getRestaurants(zip);
    }
}






