package com.hoshuttle.favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FavoriteStationRepository extends JpaRepository<FavoriteStation, Long> {
    List<FavoriteStation> findByUserId(Long userId);
    void deleteByUserIdAndStationId(Long userId, Long stationId);
}
