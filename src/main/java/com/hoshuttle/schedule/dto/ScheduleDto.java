package com.hoshuttle.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {
    private Long id;
    private Long stationId;
    private String dayType;
    private LocalTime time;
    private String busNumber;
    private String direction;
}
