package com.hoshuttle.backend.timetable;

import com.hoshuttle.backend.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetables")
@RequiredArgsConstructor
public class TimeTableController {

    private final TimeTableService timeTableService;

    @GetMapping
    public ResponseEntity<TimeTableResponse> getTimetable(
            @RequestParam Long stationId,
            @RequestParam String day) {
        return ResponseEntity.ok(timeTableService.getTimetable(stationId, day));
    }
}
