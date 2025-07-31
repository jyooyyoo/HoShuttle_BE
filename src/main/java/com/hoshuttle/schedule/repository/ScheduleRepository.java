package com.hoshuttle.schedule.repository;

import com.hoshuttle.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByStationIdAndDayType(Long stationId, Schedule.DayType dayType);
}
