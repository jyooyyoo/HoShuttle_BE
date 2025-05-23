package com.hoseobus.facility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityDto {
    private Long id;
    private String name;
    private String address;
    private String description;
    private String facilityType;
    private Double latitude;
    private Double longitude;
    private String contactNumber;
    private String operatingHours;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Entity를 DTO로 변환하는 정적 메서드
    public static FacilityDto fromEntity(Facility facility) {
        return FacilityDto.builder()
                .id(facility.getId())
                .name(facility.getName())
                .address(facility.getAddress())
                .description(facility.getDescription())
                .facilityType(facility.getFacilityType())
                .latitude(facility.getLatitude())
                .longitude(facility.getLongitude())
                .contactNumber(facility.getContactNumber())
                .operatingHours(facility.getOperatingHours())
                .isActive(facility.getIsActive())
                .createdAt(facility.getCreatedAt())
                .updatedAt(facility.getUpdatedAt())
                .build();
    }

    // DTO를 Entity로 변환하는 메서드
    public Facility toEntity() {
        return Facility.builder()
                .id(this.id)
                .name(this.name)
                .address(this.address)
                .description(this.description)
                .facilityType(this.facilityType)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .contactNumber(this.contactNumber)
                .operatingHours(this.operatingHours)
                .isActive(this.isActive)
                .build();
    }
}
