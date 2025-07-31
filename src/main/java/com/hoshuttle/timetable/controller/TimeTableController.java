package com.hoshuttle.timetable.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoseobus.backend.timetable.dto.TimeTableRequestDto;
import com.hoseobus.backend.timetable.dto.TimeTableResponseDto;
import com.hoseobus.backend.timetable.service.TimeTableBusinessService;
import com.hoseobus.backend.timetable.service.TimeTableFacadeService;
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
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
public class TimeTableController {

    private final TimeTableFacadeService facade;
    private final TimeTableBusinessService service;
    private final ObjectMapper objectMapper; // ObjectMapper 주입

    // JSON 파일 경로 상수 정의
    private static final String SHUTTLE_SCHEDULE_JSON_PATH = "data/shuttleScheduleDB.json";

    /**
     * 새로운 시간표 데이터를 저장합니다.
     * @param dto 저장할 시간표 요청 데이터
     * @return 성공 시 200 OK 응답
     */
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody TimeTableRequestDto dto) {
        facade.saveAllFromRequest(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 정류장 ID와 요일에 해당하는 시간표를 조회합니다.
     * @param stationId 정류장 ID
     * @param day 요일 (예: "월", "화", "수" 등)
     * @return 조회된 시간표 응답 DTO 목록
     */
    @GetMapping
    public ResponseEntity<List<TimeTableResponseDto>> getTimetable(
            @RequestParam Long stationId,
            @RequestParam String day
    ) {
        // 이 메서드는 데이터베이스에서 시간표를 조회하는 기존 로직을 사용합니다.
        // shuttleScheduleDB.json 파일에는 'day' 정보가 없으므로,
        // 이 메서드는 해당 JSON 파일과는 직접적으로 연관되지 않습니다.
        return ResponseEntity.ok(service.getTimetables(stationId, day));
    }

    /**
     * shuttleScheduleDB.json 파일에 있는 셔틀 시간표 데이터를 조회합니다.
     * 이 엔드포인트는 JSON 파일의 전체 내용을 반환합니다.
     * 프론트엔드에서 날짜별 필터링 또는 노선별 구분을 처리해야 합니다.
     *
     * @return 셔틀 시간표 데이터 (JSON 파일의 내용을 Map 형태로 반환)
     */
    @GetMapping("/shuttle-schedule")
    public ResponseEntity<Map<String, List<Map<String, String>>>> getShuttleSchedule() {
        try {
            // ClassPathResource를 사용하여 src/main/resources/data/shuttleScheduleDB.json 파일을 읽어옵니다.
            InputStream inputStream = new ClassPathResource(SHUTTLE_SCHEDULE_JSON_PATH).getInputStream();

            // ObjectMapper를 사용하여 JSON InputStream을 Map 형태로 파싱합니다.
            // JSON 구조가 "cheonan_to_asan": [...] 형태이므로,
            // Map<String, List<Map<String, String>>>으로 파싱하는 것이 적합합니다.
            Map<String, List<Map<String, String>>> shuttleSchedule = objectMapper.readValue(
                    inputStream,
                    new TypeReference<Map<String, List<Map<String, String>>>>() {}
            );

            return ResponseEntity.ok(shuttleSchedule);

        } catch (IOException e) {
            // 파일 읽기 또는 JSON 파싱 중 오류 발생 시 500 Internal Server Error 반환
            System.err.println("Error reading shuttle schedule JSON: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
