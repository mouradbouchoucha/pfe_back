package com.mrd.server.services.impl;

import com.mrd.server.dto.ScheduleDto;
import com.mrd.server.models.Schedule;
import com.mrd.server.repositories.ScheduleRepository;
import com.mrd.server.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleDto createSchedule(ScheduleDto scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return scheduleRepository.save(schedule).getDto();
    }

    @Override
    public ScheduleDto getScheduleById(Long id) {
        return Objects.requireNonNull(scheduleRepository.findById(id).orElse(null)).getDto();
    }

    @Override
    public List<ScheduleDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDTO) {
        Schedule existingSchedule = scheduleRepository.findById(id).orElse(null);
        if (existingSchedule == null) {
            return null; // or throw an exception
        }
        BeanUtils.copyProperties(scheduleDTO, existingSchedule);
        return scheduleRepository.save(existingSchedule).getDto();
    }

    private ScheduleDto convertToDTO(Schedule schedule) {
        ScheduleDto scheduleDTO = new ScheduleDto();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        return scheduleDTO;
    }
}
