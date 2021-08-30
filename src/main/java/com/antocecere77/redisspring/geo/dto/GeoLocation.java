package com.antocecere77.redisspring.geo.dto;

import lombok.*;

@Data
@ToString
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class GeoLocation {

    private double longitude;
    private double latitude;
}
