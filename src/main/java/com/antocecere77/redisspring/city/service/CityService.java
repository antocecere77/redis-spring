package com.antocecere77.redisspring.city.service;

import com.antocecere77.redisspring.city.client.CityClient;
import com.antocecere77.redisspring.city.dto.City;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityClient cityClient;

//    private RMapCacheReactive<String, City> cityMap;

    private RMapReactive<String, City> cityMap;

    public CityService(RedissonReactiveClient client) {
        this.cityMap = client.getMap("city", new TypedJsonJacksonCodec(String.class, City.class));
    }

    public Mono<City> getCity(final String zipCode) {
        return this.cityMap.get(zipCode)
                .onErrorResume(ex -> this.cityClient.getCity(zipCode));
    }

    //@Scheduled(fixedRate = 10_000)
    public void updateCity() {
        this.cityClient.getAll()
                .collectList()
                .map(list -> list.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
                .flatMap(this.cityMap::putAll)
                .subscribe();
    }

    /*
        get from cache
        if empty - get from db / source
            put it in cache
        return
     */
//    public Mono<City> getCity(final String zipCode) {
//        return this.cityMap.get(zipCode)
//                .switchIfEmpty(this.cityClient.getCity(zipCode)
//                        .flatMap(c -> this.cityMap.fastPut(zipCode, c, 10, TimeUnit.SECONDS)
//                        .thenReturn(c))
//                );
//    }
}















