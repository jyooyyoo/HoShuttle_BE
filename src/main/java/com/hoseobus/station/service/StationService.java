package com.hoseobus.station.service;

import java.util.List;

public interface StationService {
    Station createStation(Station station);
    Station getStation(Long id);
    List<Station> getStationsByRoute(String routeName);
    Station updateStation(Long id, Station station);
    void deleteStation(Long id);
}
