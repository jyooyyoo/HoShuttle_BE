package com.hoshuttle.station.service;

import com.hoshuttle.station.Station; // JPA 엔티티가 아닌 메모리용 Station 클래스를 import합니다.
import java.util.List;

public interface StationService {
    Station createStation(Station station);
    Station getStation(Long id);
    // 노선 이름(routeName) 대신 노선 ID(routeId)를 사용하도록 변경
    List<Station> getStationsByRoute(Long routeId);
    Station updateStation(Long id, Station station);
    void deleteStation(Long id);
}
