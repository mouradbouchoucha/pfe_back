package com.mrd.server.services;

import com.mrd.server.dto.ScheduleDto;

import java.util.List;

public interface ScheduleService {
    ScheduleDto createSchedule(ScheduleDto scheduleDTO);
    ScheduleDto getScheduleById(Long id);
    List<ScheduleDto> getAllSchedules();
    void deleteSchedule(Long id);

    ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDTO);
}
