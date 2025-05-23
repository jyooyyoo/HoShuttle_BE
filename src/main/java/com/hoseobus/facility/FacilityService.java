package com.hoseobus.facility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    // 모든 시설 조회
    @Transactional(readOnly = true)
    public List<FacilityDto> getAllFacilities() {
        return facilityRepository.findAll().stream()
                .map(FacilityDto::fromEntity)
                .collect(Collectors.toList());
    }

    // ID로 시설 조회
    @Transactional(readOnly = true)
    public Optional<FacilityDto> getFacilityById(Long id) {
        return facilityRepository.findById(id)
                .map(FacilityDto::fromEntity);
    }

    // 시설 생성
    @Transactional
    public FacilityDto createFacility(FacilityDto facilityDto) {
        Facility facility = facilityDto.toEntity();
        Facility savedFacility = facilityRepository.save(facility);
        return FacilityDto.fromEntity(savedFacility);
    }

    // 시설 수정
    @Transactional
    public Optional<FacilityDto> updateFacility(Long id, FacilityDto facilityDto) {
        return facilityRepository.findById(id)
                .map(existingFacility -> {
                    // 기존 ID 유지
                    facilityDto.setId(id);
                    Facility updatedFacility = facilityDto.toEntity();

                    // 생성일은 기존 값 유지
                    updatedFacility.setCreatedAt(existingFacility.getCreatedAt());

                    return facilityRepository.save(updatedFacility);
                })
                .map(FacilityDto::fromEntity);
    }

    // 시설 삭제
    @Transactional
    public boolean deleteFacility(Long id) {
        if (facilityRepository.existsById(id)) {
            facilityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 활성화된 시설만 조회
    @Transactional(readOnly = true)
    public List<FacilityDto> getActiveFacilities() {
        return facilityRepository.findByIsActiveTrue().stream()
                .map(FacilityDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 시설 유형별 조회
    @Transactional(readOnly = true)
    public List<FacilityDto> getFacilitiesByType(String facilityType) {
        return facilityRepository.findByFacilityType(facilityType).stream()
                .map(FacilityDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 키워드로 시설 검색 (이름 또는 주소)
    @Transactional(readOnly = true)
    public List<FacilityDto> searchFacilities(String keyword) {
        List<Facility> nameResults = facilityRepository.findByNameContaining(keyword);
        List<Facility> addressResults = facilityRepository.findByAddressContaining(keyword);

        // 중복 제거 및 결합
        return nameResults.stream()
                .filter(facility -> !addressResults.contains(facility))
                .collect(Collectors.toList())
                .stream()
                .map(FacilityDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 지역 범위 내 시설 검색
    @Transactional(readOnly = true)
    public List<FacilityDto> getFacilitiesInArea(Double latStart, Double latEnd,
                                                 Double longStart, Double longEnd) {
        return facilityRepository.findByLatitudeBetweenAndLongitudeBetween(
                        latStart, latEnd, longStart, longEnd).stream()
                .map(FacilityDto::fromEntity)
                .collect(Collectors.toList());
    }
}
