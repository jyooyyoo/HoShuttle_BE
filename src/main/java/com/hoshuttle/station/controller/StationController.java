package com.hoshuttle.station.controller;

import com.hoshuttle.station.Station; // JPA 엔티티가 아닌 메모리용 Station 클래스를 import합니다.
import com.hoshuttle.station.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    // `createStation`, `updateStation`, `deleteStation`은 메모리 기반에서는 지원되지 않으므로 제거하거나 주석 처리합니다.
    /*
    @PostMapping
    public Station createStation(@RequestBody Station station) {
        return stationService.createStation(station);
    }
    */

    @GetMapping("/{id}")
    public Station getStation(@PathVariable Long id) {
        return stationService.getStation(id);
    }

    /**
     * 특정 노선 ID에 해당하는 정류장 목록을 조회하는 엔드포인트입니다.
     * URL 경로 변수를 `String routeName`에서 `Long routeId`로 변경하여
     * `StationService`의 변경 사항과 일치시킵니다.
     * @param routeId 노선 ID
     * @return 해당 노선의 정류장 목록
     */
    @GetMapping("/route/{routeId}")
    public List<Station> getStationsByRoute(@PathVariable Long routeId) {
        return stationService.getStationsByRoute(routeId);
    }

    /*
    @PutMapping("/{id}")
    public Station updateStation(@PathVariable Long id, @RequestBody Station station) {
        return stationService.updateStation(id, station);
    }

    @DeleteMapping("/{id}")
    public void deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
    }
    */
}
