package com.hoseobus.station.service;

import com.hoseobus.station.dto.NearestStationResponseDto;

public interface NearestStationService {
    NearestStationResponseDto findNearestStation(double latitude, double longitude);
}