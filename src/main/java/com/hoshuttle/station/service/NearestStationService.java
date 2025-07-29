package com.hoshuttle.station.service;

import com.hoshuttle.station.dto.NearestStationResponseDto;

public interface NearestStationService {
    NearestStationResponseDto findNearestStation(double latitude, double longitude);
}