package com.antocecere77.redisspring.geo.service;

import com.antocecere77.redisspring.geo.dto.GeoLocation;
import com.antocecere77.redisspring.geo.dto.Restaurant;
import com.antocecere77.redisspring.geo.util.RestaurantUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RGeoReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

    private final RedissonReactiveClient client;

    private RGeoReactive<Restaurant> geo;
    private RMapReactive<String, GeoLocation> map;

    @Override
    public void run(String... args) throws Exception {
        this.geo = this.client.getGeo("restaurants", new TypedJsonJacksonCodec(Restaurant.class));
        this.map = this.client.getMap("usa", new TypedJsonJacksonCodec(String.class, GeoLocation.class));

        Flux.fromIterable(RestaurantUtil.getRestaurants())
                .flatMap(r -> this.geo.add(r.getLongitude(), r.getLatitude(), r).thenReturn(r))
                .flatMap(r -> this.map.fastPut(r.getZip(), GeoLocation.of(r.getLongitude(), r.getLatitude())))
                .doFinally(s -> System.out.println("restaurants added " + s))
                .subscribe();
    }

}









