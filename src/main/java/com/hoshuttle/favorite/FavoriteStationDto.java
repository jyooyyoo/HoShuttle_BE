package com.hoshuttle.favorite;

import com.hoshuttle.station.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteStationDto {
    private Long id;
    private Long userId;
    private Long stationId;
    private String stationName;

    public static FavoriteStationDto fromEntity(FavoriteStation favoriteStation) {
        return FavoriteStationDto.builder()
                .id(favoriteStation.getId())
                .userId(favoriteStation.getUser().getId())
                .stationId(favoriteStation.getStation().getId())
                .stationName(favoriteStation.getStation().getName())
                .build();
    }
}
