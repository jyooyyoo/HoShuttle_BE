package com.hoshuttle.schedule.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoshuttle.schedule.dto.ScheduleDto;
import com.hoshuttle.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ObjectMapper objectMapper;

    private static final String SHUTTLE_SCHEDULE_JSON_PATH = "data/shuttleScheduleDB.json";

    /**
     * 특정 정류장 ID와 요일에 해당하는 셔틀 시간표를 조회합니다.
     * @param stationId 정류장 ID
     * @param day 요일 (예: "월", "화", "수" 등)
     * @return 조회된 시간표 DTO 목록
     */
    @GetMapping
    public ResponseEntity<List<ScheduleDto>> getSchedule(
            @RequestParam Long stationId,
            @RequestParam String day
    ) {
        return ResponseEntity.ok(scheduleService.getSchedulesByStationAndDay(stationId, day));
    }

    /**
     * shuttleScheduleDB.json 파일에 있는 셔틀 시간표 데이터를 조회합니다.
     * @return 셔틀 시간표 데이터 (JSON 파일의 내용을 Map 형태로 반환)
     */
    @GetMapping("/shuttle-schedule")
    public ResponseEntity<Map<String, List<Map<String, String>>>> getShuttleSchedule() {
        try {
            InputStream inputStream = new ClassPathResource(SHUTTLE_SCHEDULE_JSON_PATH).getInputStream();
            Map<String, List<Map<String, String>>> shuttleSchedule = objectMapper.readValue(
                    inputStream,
                    new TypeReference<Map<String, List<Map<String, String>>>>() {}
            );
            return ResponseEntity.ok(shuttleSchedule);
        } catch (IOException e) {
            System.err.println("Error reading shuttle schedule JSON: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
