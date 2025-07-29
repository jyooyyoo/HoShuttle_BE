package com.hoshuttle.station.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @PostMapping
    public Station createStation(@RequestBody Station station) {
        return stationService.createStation(station);
    }

    @GetMapping("/{id}")
    public Station getStation(@PathVariable Long id) {
        return stationService.getStation(id);
    }

    @GetMapping("/route/{routeName}")
    public List<Station> getStationsByRoute(@PathVariable String routeName) {
        return stationService.getStationsByRoute(routeName);
    }

    @PutMapping("/{id}")
    public Station updateStation(@PathVariable Long id, @RequestBody Station station) {
        return stationService.updateStation(id, station);
    }

    @DeleteMapping("/{id}")
    public void deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
    }
}
