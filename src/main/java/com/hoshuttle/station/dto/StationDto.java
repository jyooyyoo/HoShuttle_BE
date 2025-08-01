package com.hoshuttle.station.dto;

import com.hoshuttle.station.entity.Station;

public class StationDto {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    // 👇 Entity -> Dto 변환용 메서드
    public static StationDto fromEntity(Station station) {
        StationDto dto = new StationDto();
        dto.id = station.getId();
        dto.name = station.getName();
        dto.latitude = station.getLatitude();
        dto.longitude = station.getLongitude();
        return dto;
    }

    // ⚠️ Getter/Setter가 없으면 아래도 추가해 주세요!
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}
