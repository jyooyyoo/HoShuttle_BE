// C:\Users\user\Documents\GitHub\HoShuttle_BE\src\main\java\com\hoshuttle\favorite\FavoriteRoute.java
package com.hoshuttle.favorite;

import com.hoshuttle.user.entity.User; // User 엔티티의 실제 패키지에 맞게 수정
import com.hoshuttle.station.entity.Station; // Station 엔티티의 실제 패키지에 맞게 수정
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime; // LocalTime 임포트 추가

@Entity
@Table(name = "favorite_route") // 테이블 이름 명시
@Getter // Getter는 필수
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 사용 시 기본 생성자는 protected가 일반적
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 빌더 패턴 사용 시 AllArgsConstructor는 private으로
@Builder // 빌더 패턴 사용
public class FavoriteRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User 엔티티와의 연관 관계

    // userId 필드는 user 객체를 통해 접근하므로 제거
    // private Long userId; // <--- 삭제

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_station_id", nullable = false)
    private Station fromStation; // 출발 정류장 엔티티와의 연관 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_station_id", nullable = false)
    private Station toStation; // 도착 정류장 엔티티와의 연관 관계

    // departureStationId, arrivalStationId 필드도 fromStation, toStation 객체를 통해 접근하므로 제거
    // private Long departureStationId; // <--- 삭제
    // private Long arrivalStationId;   // <--- 삭제

    private LocalTime preferredTime; // 선호 시간 (DTO에서 받아서 설정)

    private boolean isDeleted = false; // soft delete

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; // 업데이트 시간도 관리 (필요시)

    // 빌더 패턴을 사용하여 객체를 생성할 때 필요한 필드들을 정의합니다.
    // DTO에서 받은 ID를 사용하여 User/Station 객체를 조회 후 빌더에 넘겨주어야 합니다.
    @PrePersist // 엔티티가 저장되기 전에 실행되는 메서드
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now(); // 최초 생성 시 업데이트 시간도 동일하게 설정
    }

    @PreUpdate // 엔티티가 업데이트되기 전에 실행되는 메서드
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}