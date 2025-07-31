package com.hoshuttle.timetable.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter @Setter // @Setter 추가 (Builder 사용 시 필요)
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 해당 시간표가 어느 노선에 속하는지 명확히 연결 (Route 엔티티 직접 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private com.hoseobus.route.entity.Route route; // Route 엔티티를 직접 참조

    // 해당 정류장 정보를 직접 참조 (Station 엔티티 직접 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private com.hoseobus.station.entity.Station station; // Station 엔티티를 직접 참조

    // 같은 셔틀 운행에 속하는 정류장들을 묶어주는 순서 (예: 08:00 첫 번째 셔틀, 08:10 두 번째 셔틀)
    private Integer tripSequence; // 이 필드가 핵심입니다.

    @Enumerated(EnumType.STRING)
    private DayType dayType; // weekday / weekend (JSON에는 없지만 필요하다면 기본값 설정)

    private LocalTime arrivalTime; // 해당 정류장에 도착하는 시간

    // JSON에 "shuttle" 또는 "bus" 구분이 명확하지 않으므로, 이 필드는 일단 SHUTTLE로 고정하거나
    // 추후 필요시 외부 데이터로 판단할 수 있게 할 수 있습니다.
    @Enumerated(EnumType.STRING)
    private ScheduleType type; // SHUTTLE

    private String busNumber;  // 운행 버스 번호 (nullable, JSON에 없으므로 일단 null 또는 기본값)

    // Direction은 route 엔티티에서 유추 가능하지만, 명시적으로 필요하다면 유지
    // @Enumerated(EnumType.STRING)
    // private Direction direction; // 이제 Route가 있으므로 선택 사항

    // 해당 정류장의 노선 내 순서 (RouteStation에서 가져오거나 TimeTableSeeder에서 계산 가능)
    private Integer stationSequenceInTrip;


    public enum DayType {
        WEEKDAY, WEEKEND, ALL // ALL 추가 (JSON에 요일 구분이 없으면 ALL로 처리)
    }

    public enum ScheduleType {
        SHUTTLE, BUS
    }

    // Direction은 Route 엔티티와 중복될 수 있으므로, Route를 주 참조로 사용하는 것이 더 좋습니다.
    // public enum Direction {
    //     ASAN_TO_CHEONAN,
    //     CHEONAN_TO_ASAN
    // }
}