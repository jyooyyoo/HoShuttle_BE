package com.hoshuttle.favorite;

public interface FavoriteRouteRepository extends JpaRepository<FavoriteRoute, Long> {

    List<FavoriteRoute> findAllByUserIdAndIsDeletedFalse(Long userId);

    Optional<FavoriteRoute> findByUserIdAndDepartureStationIdAndArrivalStationIdAndIsDeletedFalse(
            Long userId, Long departureStationId, Long arrivalStationId
    );
}
