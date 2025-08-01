package com.hoshuttle.favorite;

import com.hoshuttle.user.User;
import com.hoshuttle.user.UserRepository;
import com.hoshuttle.station.Station;
import com.hoshuttle.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteStationRepository favoriteStationRepository;
    private final FavoriteRouteRepository favoriteRouteRepository;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;

    // 즐겨찾기 정거장 관련 메서드
    @Transactional
    public FavoriteStationDto addFavoriteStation(Long userId, Long stationId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Station station = stationRepository.findById(stationId).orElseThrow(() -> new RuntimeException("Station not found"));

        FavoriteStation favoriteStation = FavoriteStation.builder()
                .user(user)
                .station(station)
                .build();
        FavoriteStation saved = favoriteStationRepository.save(favoriteStation);
        return FavoriteStationDto.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<FavoriteStationDto> getUserFavoriteStations(Long userId) {
        return favoriteStationRepository.findByUserId(userId).stream()
                .map(FavoriteStationDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeFavoriteStation(Long userId, Long stationId) {
        favoriteStationRepository.deleteByUserIdAndStationId(userId, stationId);
    }

    // 즐겨찾기 노선 관련 메서드
    @Transactional
    public FavoriteRouteDto addFavoriteRoute(Long userId, Long departureStationId, Long arrivalStationId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Station departureStation = stationRepository.findById(departureStationId).orElseThrow(() -> new RuntimeException("Departure station not found"));
        Station arrivalStation = stationRepository.findById(arrivalStationId).orElseThrow(() -> new RuntimeException("Arrival station not found"));

        FavoriteRoute favoriteRoute = FavoriteRoute.builder()
                .user(user)
                .departureStation(departureStation)
                .arrivalStation(arrivalStation)
                .build();
        FavoriteRoute saved = favoriteRouteRepository.save(favoriteRoute);
        return FavoriteRouteDto.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<FavoriteRouteDto> getUserFavoriteRoutes(Long userId) {
        return favoriteRouteRepository.findByUserId(userId).stream()
                .map(FavoriteRouteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeFavoriteRoute(Long userId, Long departureStationId, Long arrivalStationId) {
        favoriteRouteRepository.deleteByUserIdAndDepartureStationIdAndArrivalStationId(userId, departureStationId, arrivalStationId);
    }
}
