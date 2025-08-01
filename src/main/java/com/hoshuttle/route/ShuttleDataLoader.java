package com.hoshuttle.route;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoshuttle.station.Station;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

/**
 * 애플리케이션 시작 시 JSON 파일을 읽어 메모리에 셔틀 데이터를 로드하는 클래스입니다.
 * CommandLineRunner를 구현하여 애플리케이션 구동 시 자동으로 실행됩니다.
 */
@Component
@RequiredArgsConstructor
public class ShuttleDataLoader implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final ShuttleDataService shuttleDataService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Shuttle Data Loader 시작: JSON에서 데이터 로드 중...");

        // 1. JSON 파일 읽기 (src/main/resources/data/shuttleScheduleDB.json)
        InputStream is = new ClassPathResource("data/shuttleScheduleDB.json").getInputStream();
        JsonNode root = objectMapper.readTree(is);

        // 2. Route 객체 생성 및 ID 할당
        List<Route> routes = new ArrayList<>();
        // "천안 ↔ 아산 캠퍼스 셔틀버스 운행 시간표" 노선
        routes.add(Route.builder().id(1L).name("cheonan_to_asan").build());
        // "아산 ↔ 천안 캠퍼스 셔틀버스 운행 시간표" 노선
        routes.add(Route.builder().id(2L).name("asan_to_cheonan").build());
        shuttleDataService.setRoutes(routes);

        // 3. Station 객체 생성 및 ID 할당
        List<Station> allStations = new ArrayList<>();
        long stationIdCounter = 1L;
        // JSON 구조에 맞춰 모든 정류장 이름을 추출하고 Station 객체로 만듭니다.
        for (String routeName : List.of("cheonan_to_asan", "asan_to_cheonan")) {
            JsonNode schedules = root.get(routeName);
            Route currentRoute = routes.stream()
                    .filter(r -> r.getName().equals(routeName))
                    .findFirst().orElseThrow();

            // 각 노선에 대한 정류장 순서를 추적하는 맵
            Map<String, Integer> stationOrderMap = new HashMap<>();

            for (JsonNode schedule : schedules) {
                // 이 반복문은 각 스케줄에 있는 정류장들을 Station 객체로 만듭니다.
                // 이미 존재하는 정류장은 건너뛰고, 각 노선에서의 순서를 기록합니다.
                Iterator<String> stationNames = schedule.fieldNames();
                while (stationNames.hasNext()) {
                    String stationName = stationNames.next();
                    if (allStations.stream().noneMatch(s -> s.getName().equals(stationName))) {
                        Station station = Station.builder()
                                .id(stationIdCounter++)
                                .name(stationName)
                                .routeName(routeName) // 이 필드는 이제 사용되지 않지만, 기존 코드 호환을 위해 남겨둡니다.
                                .routeId(currentRoute.getId())
                                .latitude(null)
                                .longitude(null)
                                .imageUrl(null)
                                .build();
                        allStations.add(station);
                    }
                    // 노선 내 정류장 순서 기록
                    if (!stationOrderMap.containsKey(stationName)) {
                        stationOrderMap.put(stationName, stationOrderMap.size() + 1);
                    }
                }
            }
        }
        shuttleDataService.setStations(allStations);

        System.out.println("총 로드된 Route 수: " + routes.size());
        System.out.println("총 로드된 Station 수: " + allStations.size());
        System.out.println("Shuttle Data Loader 완료: 데이터를 메모리에 성공적으로 로드했습니다.");
    }
}
