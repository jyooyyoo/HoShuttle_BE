// src/main/java/com/hoshuttle/station/seeder/StationSeeder.java
package com.hoshuttle.station.seeder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoshuttle.station.entity.Station;
import com.hoshuttle.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

/**
 * 애플리케이션 시작 시 JSON 파일을 읽어 셔틀 정류장 데이터를 시딩하는 클래스입니다.
 * CommandLineRunner를 구현하여 애플리케이션 구동 시 자동으로 실행됩니다.
 *
 * 이 클래스는 com.hoshuttle 패키지 구조에 맞게 import 경로를 수정했습니다.
 */
@Component
@RequiredArgsConstructor
public class StationSeeder implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final StationRepository stationRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Station Seeder 시작: JSON에서 정류장 데이터를 추출하여 저장합니다.");

        // 1. JSON 파일 읽기 (src/main/resources/data/shuttleScheduleDB.json)
        InputStream is = new ClassPathResource("data/shuttleScheduleDB.json").getInputStream();
        JsonNode root = objectMapper.readTree(is);

        // 2. 모든 정류장 이름 추출
        Set<String> stationNames = new HashSet<>();
        // JSON 구조에 맞춰 cheonan_to_asan, asan_to_cheonan 노선의 모든 정류장 이름을 추출합니다.
        for (String direction : List.of("cheonan_to_asan", "asan_to_cheonan")) {
            for (JsonNode schedule : root.get(direction)) {
                schedule.fieldNames().forEachRemaining(stationNames::add);
            }
        }

        System.out.println("총 추출된 정류장 수: " + stationNames.size());

        // 3. 추출한 정류장 이름들을 데이터베이스에 중복 없이 저장
        for (String name : stationNames) {
            // 이미 존재하는 정류장인지 확인
            if (stationRepository.findByName(name).isEmpty()) {
                Station station = Station.builder()
                        .name(name)
                        // 위도, 경도, 노선 내 순서, 노선 이름, 이미지 URL은 JSON에 없으므로 null로 설정
                        .latitude(null)
                        .longitude(null)
                        .orderInRoute(null)
                        .routeName(null)
                        .imageUrl(null)
                        .build();

                stationRepository.save(station);
                System.out.println("정류장 저장 완료: " + name);
            } else {
                System.out.println("이미 존재하는 정류장: " + name);
            }
        }

        System.out.println("Station Seeder 완료: 모든 정류장 데이터를 성공적으로 시드했습니다.");
    }
}
