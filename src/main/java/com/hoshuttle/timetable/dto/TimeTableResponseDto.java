package com.hoshuttle.timetable.dto;

@Data
@Builder
public class TimeTableResponseDto {
    private Long id;
    private Long stationId;
    private String day;
    private String time;
    private String type;
    private String busNumber;
    private String direction;
}
