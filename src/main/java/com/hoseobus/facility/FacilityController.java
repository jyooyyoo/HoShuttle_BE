package com.hoseobus.facility;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    // 모든 시설 조회
    @GetMapping
    public ResponseEntity<List<FacilityDto>> getAllFacilities() {
        List<FacilityDto> facilities = facilityService.getAllFacilities();
        return ResponseEntity.ok(facilities);
    }

    // ID로 시설 조회
    @GetMapping("/{id}")
    public ResponseEntity<FacilityDto> getFacilityById(@PathVariable Long id) {
        return facilityService.getFacilityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 시설 생성
    @PostMapping
    public ResponseEntity<FacilityDto> createFacility(@RequestBody FacilityDto facilityDto) {
        FacilityDto createdFacility = facilityService.createFacility(facilityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFacility);
    }

    // 시설 수정
    @PutMapping("/{id}")
    public ResponseEntity<FacilityDto> updateFacility(
            @PathVariable Long id,
            @RequestBody FacilityDto facilityDto) {
        return facilityService.updateFacility(id, facilityDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 시설 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        boolean deleted = facilityService.deleteFacility(id);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    // 활성화된 시설만 조회
    @GetMapping("/active")
    public ResponseEntity<List<FacilityDto>> getActiveFacilities() {
        List<FacilityDto> activeFacilities = facilityService.getActiveFacilities();
        return ResponseEntity.ok(activeFacilities);
    }

    // 시설 유형별 조회
    @GetMapping("/type/{facilityType}")
    public ResponseEntity<List<FacilityDto>> getFacilitiesByType(
            @PathVariable String facilityType) {
        List<FacilityDto> facilities = facilityService.getFacilitiesByType(facilityType);
        return ResponseEntity.ok(facilities);
    }

    // 키워드로 시설 검색
    @GetMapping("/search")
    public ResponseEntity<List<FacilityDto>> searchFacilities(
            @RequestParam String keyword) {
        List<FacilityDto> facilities = facilityService.searchFacilities(keyword);
        return ResponseEntity.ok(facilities);
    }

    // 지역 범위 내 시설 검색
    @GetMapping("/area")
    public ResponseEntity<List<FacilityDto>> getFacilitiesInArea(
            @RequestParam Double latStart,
            @RequestParam Double latEnd,
            @RequestParam Double longStart,
            @RequestParam Double longEnd) {
        List<FacilityDto> facilities = facilityService.getFacilitiesInArea(
                latStart, latEnd, longStart, longEnd);
        return ResponseEntity.ok(facilities);
    }
}
