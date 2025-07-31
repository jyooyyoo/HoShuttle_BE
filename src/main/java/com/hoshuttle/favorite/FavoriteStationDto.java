// C:\Users\user\Documents\GitHub\HoShuttle_BE\src\main\java\com\hoshuttle\favorite\FavoriteStationDto.java
package com.hoshuttle.favorite;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime; // LocalTime 임포트 추가

public class FavoriteStationDto {

    @Getter @Setter
    public static class Create {
        private Long userId;
        private Long stationId;
        private LocalTime preferredTime;
    }

    // 필요하다면 다른 DTO도 여기에 중첩 클래스로 정의할 수 있습니다.
}