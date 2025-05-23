package com.hoseobus.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    // 이름으로 시설 찾기
    Optional<Facility> findByName(String name);

    // 활성화된 시설만 찾기
    List<Facility> findByIsActiveTrue();

    // 시설 유형별로 찾기
    List<Facility> findByFacilityType(String facilityType);

    // 이름에 특정 키워드가 포함된 시설 찾기
    List<Facility> findByNameContaining(String keyword);

    // 주소에 특정 키워드가 포함된 시설 찾기
    List<Facility> findByAddressContaining(String keyword);

    // 위도와 경도 범위 내의 시설 찾기 (특정 지역 내 시설 검색)
    List<Facility> findByLatitudeBetweenAndLongitudeBetween(
            Double latStart, Double latEnd, Double longStart, Double longEnd);
}
