@Service
@RequiredArgsConstructor
public class TimeTableService {

    public TimeTableResponse getTimetable(Long stationId, String day) {
        // 실제 DB에서 가져오는 로직으로 대체 예정
        List<TimeTableResponse.ScheduleDto> schedules = new ArrayList<>();

        schedules.add(schedule("08:00", "shuttle", null));
        schedules.add(schedule("08:10", "shuttle", null));
        schedules.add(schedule("08:20", "shuttle", null));
        schedules.add(schedule("08:40", "bus", "순환5번"));
        schedules.add(schedule("08:45", "shuttle", null));
        schedules.add(schedule("08:45", "bus", "1000번"));
        schedules.add(schedule("09:00", "shuttle", null));

        TimeTableResponse response = new TimeTableResponse();
        response.setStationId(stationId);
        response.setDay(day);
        response.setSchedules(schedules);
        return response;
    }

    private TimeTableResponse.ScheduleDto schedule(String time, String type, String busNumber) {
        TimeTableResponse.ScheduleDto dto = new TimeTableResponse.ScheduleDto();
        dto.setTime(time);
        dto.setType(type);
        dto.setBusNumber(busNumber);
        return dto;
    }
}
