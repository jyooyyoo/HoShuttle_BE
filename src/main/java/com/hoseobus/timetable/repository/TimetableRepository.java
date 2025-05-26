package com.hoseobus.timetable.repository;

// timetable/repository/com.hoseobus.timetable.repository.TimeTableRepository.java
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    List<TimeTable> findByStationIdAndDayType(Long stationId, TimeTable.DayType dayType);
}
