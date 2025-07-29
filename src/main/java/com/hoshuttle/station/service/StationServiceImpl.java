package com.hoshuttle.station.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Override
    public Station createStation(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public Station getStation(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));
    }

    @Override
    public List<Station> getStationsByRoute(String routeName) {
        return stationRepository.findAllByRouteNameOrderByOrderInRoute(routeName);
    }

    @Override
    public Station updateStation(Long id, Station updated) {
        Station station = getStation(id);
        station.setName(updated.getName());
        station.setLatitude(updated.getLatitude());
        station.setLongitude(updated.getLongitude());
        station.setOrderInRoute(updated.getOrderInRoute());
        station.setRouteName(updated.getRouteName());
        return stationRepository.save(station);
    }

    @Override
    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }
}
