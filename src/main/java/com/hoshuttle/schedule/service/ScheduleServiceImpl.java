package com.hoshuttle.schedule.service;

import com.hoshuttle.schedule.domain.Schedule;
import com.hoshuttle.schedule.dto.ScheduleDto;
import com.hoshuttle.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public List<ScheduleDto> getSchedulesByStationAndDay(Long stationId, String day) {
        Schedule.DayType dayType = Schedule.DayType.valueOf(day.toUpperCase());
        return scheduleRepository.findAllByStationIdAndDayType(stationId, dayType)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ScheduleDto toDto(Schedule schedule) {
        return ScheduleDto.builder()
                .id(schedule.getId())
                .stationId(schedule.getStationId())
                .dayType(schedule.getDayType().name())
                .time(schedule.getTime())
                .busNumber(schedule.getBusNumber())
                .direction(schedule.getDirection() != null ? schedule.getDirection().name() : null)
                .build();
    }
}
