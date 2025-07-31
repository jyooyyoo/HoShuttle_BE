package com.hoshuttle.route;

import com.hoshuttle.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Optional 임포트 추가

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    // 노선 이름을 기준으로 Route 엔티티를 조회하는 메서드
    Optional<Route> findByName(String name); // <--- 이 라인 추가
}