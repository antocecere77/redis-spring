package com.antocecere77.redisspring.city.dto;

import lombok.*;

@Data
@With
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class City {

    private String zip;

    private String city;

    private String stateName;

    private int temperature;
}
