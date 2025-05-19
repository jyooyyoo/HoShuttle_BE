package com.hoshuttle.backend.route;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 경로가 존재하지 않습니다. id=" + id));
    }

    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    public Route updateRoute(Long id, Route updatedRoute) {
        Route route = getRouteById(id);
        // 필드 직접 수정 (예시)
        route.setName(updatedRoute.getName());
        // 다른 필요한 필드들도 여기에 추가
        return route;
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }
}
