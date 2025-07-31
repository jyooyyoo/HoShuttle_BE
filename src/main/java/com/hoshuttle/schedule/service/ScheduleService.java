package com.hoshuttle.schedule.service;

import com.hoshuttle.schedule.dto.ScheduleDto;
import java.util.List;

public interface ScheduleService {
    List<ScheduleDto> getSchedulesByStationAndDay(Long stationId, String day);
}
