package com.hoshuttle.backend.timetable;

import java.util.List;

public interface TimetableService {

    TimetableDto createTimetable(TimetableDto dto);

    TimetableDto getTimetable(Long id);

    List<TimetableDto> getAllTimetables();

    TimetableDto updateTimetable(Long id, TimetableDto dto);

    void deleteTimetable(Long id);
}
