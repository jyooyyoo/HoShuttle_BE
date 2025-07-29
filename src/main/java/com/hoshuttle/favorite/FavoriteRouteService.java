@Service
@RequiredArgsConstructor
public class FavoriteRouteService {

    private final FavoriteRouteRepository favoriteRouteRepository;

    public FavoriteRoute createFavoriteRoute(Long userId, Long departureStationId, Long arrivalStationId, LocalTime preferredTime) {
        FavoriteRoute favoriteRoute = new FavoriteRoute();
        favoriteRoute.setUserId(userId);
        favoriteRoute.setDepartureStationId(departureStationId);
        favoriteRoute.setArrivalStationId(arrivalStationId);
        favoriteRoute.setPreferredTime(preferredTime);
        favoriteRoute.setCreatedAt(LocalDateTime.now());
        favoriteRoute.setUpdatedAt(LocalDateTime.now());
        return favoriteRouteRepository.save(favoriteRoute);
    }

    public List<FavoriteRoute> getFavoritesByUser(Long userId) {
        return favoriteRouteRepository.findAllByUserIdAndIsDeletedFalse(userId);
    }

    public void deleteFavoriteRoute(Long routeId) {
        FavoriteRoute favoriteRoute = favoriteRouteRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("즐겨찾는 경로를 찾을 수 없습니다."));
        favoriteRoute.setIsDeleted(true);
        favoriteRoute.setUpdatedAt(LocalDateTime.now());
        favoriteRouteRepository.save(favoriteRoute);
    }
}
