package com.hoshuttle.station.service;

import com.hoshuttle.station.dto.NearestStationResponseDto;
import com.hoshuttle.station.Station;
import com.hoshuttle.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NearestStationServiceImpl implements NearestStationService {

    private final StationRepository stationRepository;

    @Override
    public NearestStationResponseDto findNearestStation(double latitude, double longitude) {
        Station nearest = stationRepository.findNearest(latitude, longitude);

        if (nearest == null) {
            throw new RuntimeException("가까운 정류장을 찾을 수 없습니다.");
        }

        return new NearestStationResponseDto(
                nearest.getId(),
                nearest.getName(),
                nearest.getLatitude(),
                nearest.getLongitude(),
                nearest.getRouteName()
        );
    }
}