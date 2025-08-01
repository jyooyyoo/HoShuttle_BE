package com.hoshuttle.favorite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteRouteDto {
    private Long id;
    private Long userId;
    private Long departureStationId;
    private String departureStationName;
    private Long arrivalStationId;
    private String arrivalStationName;

    public static FavoriteRouteDto fromEntity(FavoriteRoute favoriteRoute) {
        return FavoriteRouteDto.builder()
                .id(favoriteRoute.getId())
                .userId(favoriteRoute.getUser().getId())
                .departureStationId(favoriteRoute.getDepartureStation().getId())
                .departureStationName(favoriteRoute.getDepartureStation().getName())
                .arrivalStationId(favoriteRoute.getArrivalStation().getId())
                .arrivalStationName(favoriteRoute.getArrivalStation().getName())
                .build();
    }
}
