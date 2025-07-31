package com.hoshuttle.favorite;

public interface FavoriteStationRepository extends JpaRepository<FavoriteStation, Long> {

    List<FavoriteStation> findAllByUserIdAndIsDeletedFalse(Long userId);

    Optional<FavoriteStation> findByUserIdAndStationIdAndIsDeletedFalse(Long userId, Long stationId);
}
