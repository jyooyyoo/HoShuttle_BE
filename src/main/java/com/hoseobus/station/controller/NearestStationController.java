package com.hoseobus.station.controller;

import com.hoseobus.station.dto.NearestStationResponseDto;
import com.hoseobus.station.service.NearestStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stations")
public class NearestStationController {

    private final NearestStationService nearestStationService;

    @GetMapping("/nearest")
    public NearestStationResponseDto getNearestStation(
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {
        return nearestStationService.findNearestStation(latitude, longitude);
    }
}