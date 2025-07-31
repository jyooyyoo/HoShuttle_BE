package com.hoshuttle.timetable.service;


@Service
@RequiredArgsConstructor
public class TimeTableBusinessService {

    private final TimeTableCrudService crudService;

    public List<TimeTableResponseDto> getTimetables(Long stationId, String day) {
        var dayType = TimeTable.DayType.valueOf(day.toUpperCase());
        return crudService.findByStationAndDay(stationId, dayType).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TimeTableResponseDto toDto(TimeTable t) {
        return TimeTableResponseDto.builder()
                .id(t.getId())
                .stationId(t.getStationId())
                .day(t.getDayType().name().toLowerCase())
                .time(t.getTime().toString())
                .type(t.getType().name().toLowerCase())
                .busNumber(t.getBusNumber())
                .direction(t.getDirection() != null ? t.getDirection().name() : null)
                .build();
    }
}
