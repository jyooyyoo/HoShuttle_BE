package com.hoshuttle.timetable.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoseobus.route.entity.Route;
import com.hoseobus.route.repository.RouteRepository;
import com.hoseobus.station.entity.Station;
import com.hoseobus.station.repository.StationRepository;
import com.hoseobus.timetable.domain.TimeTable;
import com.hoseobus.timetable.repository.TimetableRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TimetableSeeder {

    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final TimetableRepository timetableRepository;
    private final ObjectMapper objectMapper; // JSON 파싱을 위한 ObjectMapper

    private static final String SHUTTLE_SCHEDULE_JSON_PATH = "data/ShuttleScheduleDB.json";

    @PostConstruct // 애플리케이션 시작 시 자동으로 실행
    @Transactional // 데이터베이스 트랜잭션 관리
    public void seedTimetableData() {
        System.out.println("Timetable Seeder 시작: 기존 시간표 데이터를 삭제하고 새로 시드합니다.");

        // 기존 시간표 데이터 삭제 (매번 시드 시 중복 방지)
        timetableRepository.deleteAllInBatch();
        System.out.println("기존 시간표 데이터 삭제 완료.");

        try {
            // 1. JSON 파일 읽기
            ClassPathResource resource = new ClassPathResource(SHUTTLE_SCHEDULE_JSON_PATH);
            InputStream inputStream = resource.getInputStream();

            // JSON 구조에 맞춰 Map<String, List<Map<String, String>>>으로 파싱
            // { "cheonan_to_asan": [ { "정류장1": "시간1", "정류장2": "시간2", ... }, ... ] }
            TypeReference<Map<String, List<Map<String, String>>>> typeRef =
                    new TypeReference<>() {};
            Map<String, List<Map<String, String>>> shuttleData =
                    objectMapper.readValue(inputStream, typeRef);

            System.out.println("JSON 파일 파싱 완료.");

            // 2. 파싱된 데이터로 TimeTable 엔티티 생성 및 저장
            processTimetableEntries(shuttleData.get("cheonan_to_asan"), "천안 ↔ 아산 캠퍼스 셔틀버스 운행 시간표", TimeTable.Direction.CHEONAN_TO_ASAN);
            processTimetableEntries(shuttleData.get("asan_to_cheonan"), "아산 ↔ 천안 캠퍼스 셔틀버스 운행 시간표", TimeTable.Direction.ASAN_TO_CHEONAN);

            System.out.println("Timetable Seeder 완료: 모든 시간표 데이터를 성공적으로 시드했습니다.");

        } catch (IOException e) {
            System.err.println("Timetable Seeder 오류: JSON 파일을 읽거나 파싱하는 중 문제가 발생했습니다.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Timetable Seeder 오류: 데이터 시딩 중 예상치 못한 오류 발생.");
            e.printStackTrace();
        }
    }

    private void processTimetableEntries(List<Map<String, String>> entries, String routeName, TimeTable.Direction direction) {
        if (entries == null) {
            System.out.println(routeName + "에 해당하는 시간표 데이터가 JSON에 없습니다. 스킵합니다.");
            return;
        }

        // Route 엔티티 조회 (RouteRepository 필요)
        Optional<Route> optionalRoute = routeRepository.findByName(routeName);
        if (optionalRoute.isEmpty()) {
            System.err.println("오류: " + routeName + "에 해당하는 Route를 찾을 수 없습니다. 이 노선에 대한 시간표 시딩을 건너뜝니다.");
            return;
        }
        Route route = optionalRoute.get();
        System.out.println("Route '" + route.getName() + "' (ID: " + route.getId() + ") 를 찾았습니다.");

        int tripSequence = 0; // 각 셔틀 운행을 구분하는 순서
        for (Map<String, String> trip : entries) {
            tripSequence++;
            int stationSequenceInTrip = 0; // 해당 셔틀 운행 내 정류장 순서

            // JSON의 Map은 순서가 보장되지 않을 수 있으므로, RouteStation에서 순서를 가져오는 것이 좋습니다.
            // 그러나 현재 JSON 구조는 정류장 순서대로 키-값 쌍이 배열되어 있다고 가정합니다.
            // 만약 순서가 중요하다면 RouteStation에서 조회하여 정렬된 정류장 목록을 가져온 후 사용해야 합니다.

            // 임시 방편으로, JSON Map의 EntrySet을 순회하며 정류장 순서를 만듭니다.
            // 이 방식은 Map 구현체에 따라 순서가 달라질 수 있으니 주의하세요.
            // 일반적으로 LinkedHashMap이 사용되어 JSON 파서에서 순서를 보장하지만, 보장되는 것은 아닙니다.
            // 가장 정확한 방법은 RouteStation을 통해 해당 Route의 정류장 목록을 순서대로 가져와서 매핑하는 것입니다.

            // 여기서는 JSON Map의 순서를 따르고, StationService 등을 통해 stationName으로 Station 객체 조회를 진행합니다.
            for (Map.Entry<String, String> entry : trip.entrySet()) {
                String stationName = entry.getKey();
                String timeStr = entry.getValue();

                // 시간 파싱
                LocalTime arrivalTime = null;
                try {
                    arrivalTime = LocalTime.parse(timeStr);
                } catch (Exception e) {
                    System.err.println("경고: 유효하지 않은 시간 형식 발견 ('" + timeStr + "'). 해당 시간표 항목을 건너뜀.");
                    continue;
                }

                // Station 엔티티 조회 (StationRepository 필요)
                Optional<Station> optionalStation = stationRepository.findByName(stationName);
                if (optionalStation.isEmpty()) {
                    System.err.println("경고: '" + stationName + "'에 해당하는 Station을 찾을 수 없습니다. 이 시간표 항목을 건너뜝니다.");
                    continue;
                }
                Station station = optionalStation.get();

                stationSequenceInTrip++;

                // TimeTable 엔티티 생성 및 저장
                TimeTable timeTable = TimeTable.builder()
                        .route(route)
                        .station(station)
                        .tripSequence(tripSequence)
                        .dayType(TimeTable.DayType.ALL) // JSON에 요일 구분 없으므로 ALL로 설정
                        .arrivalTime(arrivalTime)
                        .type(TimeTable.ScheduleType.SHUTTLE) // 셔틀로 고정
                        .busNumber(null) // JSON에 없으므로 null
                        // .direction(direction) // Route 엔티티로 충분하므로 주석 처리 (필요시 다시 사용)
                        .stationSequenceInTrip(stationSequenceInTrip)
                        .build();

                // 중복 방지 로직 (필요하다면 활성화. deleteAllInBatch를 사용하면 필요 없음)
                // if (!timetableRepository.existsByRouteAndStationAndArrivalTimeAndDayTypeAndTripSequence(
                //         route, station, arrivalTime, TimeTable.DayType.ALL, tripSequence)) {
                //     timetableRepository.save(timeTable);
                // } else {
                //     System.out.println("중복 시간표 항목 발견: " + route.getName() + " " + station.getName() + " " + arrivalTime);
                // }
                timetableRepository.save(timeTable); // deleteAllInBatch를 사용하므로 중복 검사 생략 가능
            }
        }
        System.out.println(routeName + " 시간표 데이터 시딩 완료. 총 " + entries.size() + "개의 셔틀 운행을 처리했습니다.");
    }
}