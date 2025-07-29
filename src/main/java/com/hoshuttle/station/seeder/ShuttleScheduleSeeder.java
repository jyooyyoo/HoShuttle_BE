package com.hoshuttle.station.seeder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoseobus.station.entity.Station;
import com.hoseobus.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

@Component
@RequiredArgsConstructor
public class StationSeeder implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final StationRepository stationRepository;

    @Override
    public void run(String... args) throws Exception {
        // 1. JSON 파일 읽기
        InputStream is = new ClassPathResource("data/ShuttleScheduleDB.json").getInputStream();
        JsonNode root = objectMapper.readTree(is);

        // 2. 모든 정류장 이름 추출
        Set<String> stationNames = new HashSet<>();
        for (String direction : List.of("cheonan_to_asan", "asan_to_cheonan")) {
            for (JsonNode schedule : root.get(direction)) {
                schedule.fieldNames().forEachRemaining(stationNames::add);
            }
        }

        System.out.println("총 정류장 수: " + stationNames.size());

        // 3. 중복 없이 저장
        for (String name : stationNames) {
            if (stationRepository.findByName(name).isEmpty()) {
                Station station = Station.builder()
                        .name(name)
                        .latitude(null)
                        .longitude(null)
                        .orderInRoute(null)
                        .routeName(null)
                        .imageUrl(null)
                        .build();

                stationRepository.save(station);
                System.out.println("저장 완료: " + name);
            } else {
                System.out.println("이미 존재함: " + name);
            }
        }
    }
}
