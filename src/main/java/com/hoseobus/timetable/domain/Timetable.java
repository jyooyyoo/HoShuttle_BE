package com.hoseobus.timetable.domain;

// timetable/domain/com.hoseobus.timetable.domain.TimeTable.java
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stationId;

    @Enumerated(EnumType.STRING)
    private DayType dayType; // weekday / weekend

    private LocalTime time;

    @Enumerated(EnumType.STRING)
    private ScheduleType type; // shuttle / bus

    private String busNumber;  // nullable

    @Enumerated(EnumType.STRING)
    private Direction direction;  // nullable

    public enum DayType {
        WEEKDAY, WEEKEND
    }

    public enum ScheduleType {
        SHUTTLE, BUS
    }

    public enum Direction {
        ASAN_TO_CHEONAN,
        CHEONAN_TO_ASAN
    }
}
