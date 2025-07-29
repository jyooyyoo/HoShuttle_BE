// timetable/repository/TimetableRepository.java
package com.hoseobus.timetable.repository;

import com.hoseobus.timetable.domain.TimeTable; // 수정된 엔티티 이름에 맞춤
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalTime;

@Repository
public interface TimetableRepository extends JpaRepository<TimeTable, Long> {
    // 필요에 따라 추가적인 조회 메소드를 정의할 수 있습니다.
    // 예: 특정 노선의 특정 날짜/시간 이후 시간표 조회 등
    List<TimeTable> findByRoute_NameAndDayTypeOrderByArrivalTimeAsc(String routeName, TimeTable.DayType dayType);
    List<TimeTable> findByRoute_IdAndStation_IdAndDayTypeOrderByArrivalTimeAsc(Long routeId, Long stationId, TimeTable.DayType dayType);

    // Seeder에서 중복 저장을 막기 위해 사용될 수 있습니다.
    boolean existsByRouteAndStationAndArrivalTimeAndDayTypeAndTripSequence(
            com.hoseobus.route.entity.Route route,
            com.hoseobus.station.entity.Station station,
            LocalTime arrivalTime,
            TimeTable.DayType dayType,
            Integer tripSequence
    );

    // Seeder 실행 전 모든 시간표를 삭제할 때 사용
    void deleteAllInBatch(); // 대량 삭제에 효율적
}