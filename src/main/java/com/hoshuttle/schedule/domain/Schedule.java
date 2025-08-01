package com.hoshuttle.schedule.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;


@Table(name = "schedule")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "station_id", nullable = false)
    private Long stationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_type", nullable = false)
    private DayType dayType;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private String busNumber;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    public enum DayType {
        WEEKDAY, WEEKEND
    }

    public enum Direction {
        TO_HOSHU, FROM_HOSHU
    }
}
