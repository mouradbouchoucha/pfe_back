package com.mrd.server.services;

import com.mrd.server.dto.ScheduleDto;
import com.mrd.server.dto.TrainerDto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public interface ScheduleService {
    ScheduleDto createSchedule(ScheduleDto scheduleDTO);
    ScheduleDto getScheduleById(Long id);
    List<ScheduleDto> getAllSchedules();
    void deleteSchedule(Long id);

    ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDTO);

    List<ScheduleDto> getScheduleByCourseId(Long courseId);

    List<ScheduleDto> getScheduleByTrainerId(Long trainerId);

    List<ScheduleDto> getScheduleByTrainerIdAndCourseId(Long trainerId, Long courseId);

    ScheduleDto getScheduleByStartDateTime(LocalDateTime startDateTime);

    List<TrainerDto> getAvailableTrainers(LocalDateTime startDateTime, double duration);
}
