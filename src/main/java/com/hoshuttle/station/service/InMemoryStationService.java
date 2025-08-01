package com.hoshuttle.station.service;

import com.hoshuttle.route.ShuttleDataService;
import com.hoshuttle.station.Station;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 데이터베이스 대신 메모리에 있는 데이터를 사용하는 StationService 구현체입니다.
 */
@Service
@RequiredArgsConstructor
public class InMemoryStationService implements StationService {

    private final ShuttleDataService shuttleDataService;

    // TODO: 이 메서드는 더 이상 필요하지 않습니다. 기존 컨트롤러 로직을 수정해야 합니다.
    @Override
    public Station createStation(Station station) {
        throw new UnsupportedOperationException("이 서비스는 메모리 데이터를 사용하므로 'createStation'은 지원하지 않습니다.");
    }

    @Override
    public Station getStation(Long id) {
        return shuttleDataService.findStationById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));
    }

    /**
     * 특정 노선 ID(routeId)에 해당하는 정류장 목록을 조회합니다.
     * @param routeId 노선 ID
     * @return 해당 노선의 정류장 목록
     */
    @Override
    public List<Station> getStationsByRoute(Long routeId) {
        return shuttleDataService.getAllStations().stream()
                .filter(s -> s.getRouteId().equals(routeId))
                .collect(Collectors.toList());
    }

    // TODO: 이 메서드는 더 이상 필요하지 않습니다. 기존 컨트롤러 로직을 수정해야 합니다.
    @Override
    public Station updateStation(Long id, Station updated) {
        throw new UnsupportedOperationException("이 서비스는 메모리 데이터를 사용하므로 'updateStation'은 지원하지 않습니다.");
    }

    // TODO: 이 메서드는 더 이상 필요하지 않습니다. 기존 컨트롤러 로직을 수정해야 합니다.
    @Override
    public void deleteStation(Long id) {
        throw new UnsupportedOperationException("이 서비스는 메모리 데이터를 사용하므로 'deleteStation'은 지원하지 않습니다.");
    }
}
