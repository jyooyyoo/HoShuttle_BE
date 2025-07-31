package com.hoshuttle.favorite;

@Service
@RequiredArgsConstructor
public class FavoriteStationService {

    private final FavoriteStationRepository favoriteStationRepository;

    public FavoriteStation createFavoriteStation(Long userId, Long stationId, LocalTime preferredTime) {
        FavoriteStation favoriteStation = new FavoriteStation();
        favoriteStation.setUserId(userId);
        favoriteStation.setStationId(stationId);
        favoriteStation.setPreferredTime(preferredTime);
        favoriteStation.setCreatedAt(LocalDateTime.now());
        favoriteStation.setUpdatedAt(LocalDateTime.now());
        return favoriteStationRepository.save(favoriteStation);
    }

    public List<FavoriteStation> getFavoritesByUser(Long userId) {
        return favoriteStationRepository.findAllByUserIdAndIsDeletedFalse(userId);
    }

    public void deleteFavoriteStation(Long stationId) {
        FavoriteStation favoriteStation = favoriteStationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException("즐겨찾는 정류장을 찾을 수 없습니다."));
        favoriteStation.setIsDeleted(true);
        favoriteStation.setUpdatedAt(LocalDateTime.now());
        favoriteStationRepository.save(favoriteStation);
    }
}
