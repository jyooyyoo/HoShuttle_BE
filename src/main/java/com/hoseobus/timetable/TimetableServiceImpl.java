package com.hoshuttle.backend.timetable;

import com.hoshuttle.backend.common.CustomException;
import com.hoshuttle.backend.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;

    @Override
    public TimetableDto createTimetable(TimetableDto dto) {
        Timetable saved = timetableRepository.save(dto.toEntity());
        return TimetableDto.fromEntity(saved);
    }

    @Override
    public TimetableDto getTimetable(Long id) {
        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return TimetableDto.fromEntity(timetable);
    }

    @Override
    public List<TimetableDto> getAllTimetables() {
        return timetableRepository.findAll().stream()
                .map(TimetableDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TimetableDto updateTimetable(Long id, TimetableDto dto) {
        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        timetable.setRouteName(dto.getRouteName());
        timetable.setStationName(dto.getStationName());
        timetable.setDepartureTime(dto.getDepartureTime());
        timetable.setArrivalTime(dto.getArrivalTime());

        return TimetableDto.fromEntity(timetableRepository.save(timetable));
    }

    @Override
    public void deleteTimetable(Long id) {
        timetableRepository.deleteById(id);
    }
}
