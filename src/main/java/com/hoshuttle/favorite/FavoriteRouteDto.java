// C:\Users\user\Documents\GitHub\HoShuttle_BE\src\main\java\com\hoshuttle\favorite\FavoriteRouteDto.java
package com.hoshuttle.favorite;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime; // LocalTime 임포트 추가

public class FavoriteRouteDto {

    @Getter @Setter
    public static class Create {
        private Long userId; // DTO에서는 ID를 직접 받는 것이 일반적
        private Long departureStationId;
        private Long arrivalStationId;
        private LocalTime preferredTime;
    }

    // 필요하다면 다른 DTO (예: Response, Update 등)도 여기에 중첩 클래스로 정의할 수 있습니다.
}