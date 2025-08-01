package com.hoshuttle.station.service;

import com.hoshuttle.station.entity.Station;
import java.util.List;

public interface StationService {
    Station createStation(Station station);
    Station getStation(Long id);
    List<Station> getStationsByRoute(String routeName);
    Station updateStation(Long id, Station station);
    void deleteStation(Long id);
}
