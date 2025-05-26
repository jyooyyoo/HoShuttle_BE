public class TimeTableResponse {
    private Long stationId;
    private String day;
    private List<ScheduleDto> schedules;

    @Getter @Setter
    public static class ScheduleDto {
        private String time;
        private String type;
        private String busNumber; // type이 bus일 때만 포함
    }

    // 생성자, getter, setter 등
}
