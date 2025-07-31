// C:\Users\user\Documents\GitHub\HoShuttle_BE\src\main\java\com\hoshuttle\favorite\FavoriteStation.java
package com.hoshuttle.favorite;

import com.hoshuttle.user.entity.User; // User 엔티티의 실제 패키지에 맞게 수정
import com.hoshuttle.station.entity.Station; // Station 엔티티의 실제 패키지에 맞게 수정
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime; // LocalTime 임포트 추가

@Entity
@Table(name = "favorite_station") // 테이블 이름 명시 (필수 아님, 명확성을 위해)
@Getter // Getter는 필수
@Setter // DTO에서 받은 값으로 설정할 때 Setter 필요 (또는 빌더에 모든 필드를 넣거나)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 사용 시 기본 생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 빌더 패턴 사용 시
@Builder // 빌더 패턴 사용
public class FavoriteStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User 엔티티와의 연관 관계

    // userId 필드는 user 객체를 통해 접근하므로 제거
    // private Long userId; // <--- 삭제

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station; // Station 엔티티와의 연관 관계

    // stationId 필드는 station 객체를 통해 접근하므로 제거
    // private Long stationId; // <--- 삭제

    private LocalTime preferredTime; // 선호 시간

    private boolean isDeleted = false; // soft delete

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자나 빌더를 통해 초기화 로직 추가
    // FavoriteRoute와 유사하게 @Builder를 사용하고 @PrePersist 등을 활용하는 것이 좋습니다.
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}