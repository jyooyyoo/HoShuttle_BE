package com.hoshuttle.backend.station;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {
    List<Station> findAllByRouteNameOrderByOrderInRoute(String routeName);
}
