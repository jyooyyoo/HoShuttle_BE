// timetable/dto/TimeTableRequestDto.java
@Data
public class TimeTableRequestDto {
    private Long stationId;
    private String day;
    private List<ScheduleDto> schedules;

    @Data
    public static class ScheduleDto {
        private String time;
        private String type;
        private String busNumber;
        private String direction;
    }
}
