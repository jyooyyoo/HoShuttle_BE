package com.hoshuttle.backend.timetable;

import com.hoshuttle.backend.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetables")
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;

    @PostMapping
    public BaseResponse<TimetableDto> create(@RequestBody TimetableDto dto) {
        return new BaseResponse<>(timetableService.createTimetable(dto));
    }

    @GetMapping("/{id}")
    public BaseResponse<TimetableDto> get(@PathVariable Long id) {
        return new BaseResponse<>(timetableService.getTimetable(id));
    }

    @GetMapping
    public BaseResponse<List<TimetableDto>> getAll() {
        return new BaseResponse<>(timetableService.getAllTimetables());
    }

    @PutMapping("/{id}")
    public BaseResponse<TimetableDto> update(@PathVariable Long id, @RequestBody TimetableDto dto) {
        return new BaseResponse<>(timetableService.updateTimetable(id, dto));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<String> delete(@PathVariable Long id) {
        timetableService.deleteTimetable(id);
        return new BaseResponse<>("삭제 완료");
    }
}
