// timetable/facade/TimeTableFacadeService.java
@Service
@RequiredArgsConstructor
public class TimeTableFacadeService {

    private final TimeTableCrudService crudService;

    public void saveAllFromRequest(TimeTableRequestDto dto) {
        for (TimeTableRequestDto.ScheduleDto schedule : dto.getSchedules()) {
            TimeTable entity = TimeTable.builder()
                    .stationId(dto.getStationId())
                    .dayType(TimeTable.DayType.valueOf(dto.getDay().toUpperCase()))
                    .time(LocalTime.parse(schedule.getTime()))
                    .type(TimeTable.ScheduleType.valueOf(schedule.getType().toUpperCase()))
                    .busNumber(schedule.getBusNumber())
                    .direction(schedule.getDirection() != null ?
                            TimeTable.Direction.valueOf(schedule.getDirection().toUpperCase()) : null)
                    .build();
            crudService.save(entity);
        }
    }
}
