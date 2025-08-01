package com.hoshuttle.route;

import com.hoshuttle.station.Station;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 메모리에 셔틀 노선 및 정류장 데이터를 저장하고 관리하는 서비스입니다.
 * 이 클래스는 싱글톤으로 동작하며, 다른 서비스에서 주입받아 데이터를 사용할 수 있습니다.
 */
@Service
public class ShuttleDataService {

    private final List<Route> routes = new ArrayList<>();
    private final List<Station> stations = new ArrayList<>();

    public void setRoutes(List<Route> loadedRoutes) {
        this.routes.clear();
        this.routes.addAll(loadedRoutes);
    }

    public void setStations(List<Station> loadedStations) {
        this.stations.clear();
        this.stations.addAll(loadedStations);
    }

    public List<Route> getAllRoutes() {
        return List.copyOf(routes);
    }

    public List<Station> getAllStations() {
        return List.copyOf(stations);
    }

    public Optional<Route> findRouteById(Long id) {
        return routes.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public Optional<Station> findStationById(Long id) {
        return stations.stream().filter(s -> s.getId().equals(id)).findFirst();
    }
}
