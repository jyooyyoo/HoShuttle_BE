package com.hoshuttle.station.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NearestStationResponseDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String routeName;
}