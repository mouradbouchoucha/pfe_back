package com.mrd.server.services.impl;

import com.mrd.server.dto.ScheduleDto;
import com.mrd.server.dto.TrainerDto;
import com.mrd.server.models.*;
import com.mrd.server.repositories.*;
import com.mrd.server.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainerRepository trainerRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public ScheduleDto createSchedule(ScheduleDto scheduleDTO, Long course_id) {
        Course course = courseRepository.findById(course_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));

        Trainer trainer = trainerRepository.findById(scheduleDTO.getTrainer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid trainer ID"));
        Subject subject = subjectRepository.findById(scheduleDTO.getSubject().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subject ID"));

//        if (scheduleDTO.getSubject() != null) {
//            subject = scheduleDTO.getSubject();
//                    ;
//        }

        LocalDateTime startDateTime = scheduleDTO.getStartDateTime();
        LocalDateTime endDateTime = startDateTime.plusMinutes((long) (scheduleDTO.getDuration() * 60));
        //System.out.println(startDateTime);
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setCourse(course);
        schedule.setTrainer(trainer);
        schedule.setSubject(subject);
//        schedule.setResource(resourceRepository.findById(1L).orElse(null));
        schedule.setStartDateTime(startDateTime);
//        schedule.setEndDateTime(endDateTime);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return savedSchedule.getDto();
    }



    @Override
    public ScheduleDto getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Schedule not found for id: " + id));
    }

    @Override
    public List<ScheduleDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found for id: " + id);
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDTO) {
        return scheduleRepository.findById(id)
                .map(existingSchedule -> {
                    BeanUtils.copyProperties(scheduleDTO, existingSchedule);
                    existingSchedule.setStartDateTime(scheduleDTO.getStartDateTime());
                    existingSchedule.setDuration(scheduleDTO.getDuration());
                    return convertToDTO(scheduleRepository.save(existingSchedule));
                })
                .orElseThrow(() -> new RuntimeException("Schedule not found for id: " + id));
    }

    @Override
    public List<ScheduleDto> getScheduleByCourseId(Long courseId) {
        return scheduleRepository.findAllByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> getScheduleByTrainerId(Long trainerId) {
        return scheduleRepository.findAllByTrainerId(trainerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> getScheduleByTrainerIdAndCourseId(Long trainerId, Long courseId) {
        return scheduleRepository.findByCourseIdAndTrainerId(trainerId, courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDto getScheduleByStartDateTime(LocalDateTime startDateTime) {
        return scheduleRepository.findByStartDateTime(startDateTime)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Schedule not found for start date and time: " + startDateTime));
    }

    private ScheduleDto convertToDTO(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto();
        BeanUtils.copyProperties(schedule, scheduleDto);

        if (schedule.getCourse() != null) {
            scheduleDto.setCourse(schedule.getCourse().getDto());
        }
//        if (schedule.getResource() != null) {
//            scheduleDto.setResource(schedule.getResource().getDto());
//        }
        if (schedule.getSubject() != null) {
            //to verify
            scheduleDto.setSubject(schedule.getSubject().getDto());
        }
        if (schedule.getTrainer() != null) {
            scheduleDto.setTrainer(schedule.getTrainer().getDto());
        }

        return scheduleDto;
    }

    @Override
    public List<TrainerDto> getAvailableTrainers(LocalDateTime startDateTime, double duration) {
        // Convert duration from double to long (assuming duration is in hours)
        long durationInHours = (long) duration;

        LocalDateTime endDateTime = startDateTime.plusHours(durationInHours);

        List<Trainer> allTrainers = trainerRepository.findAll();
        List<Schedule> conflictingSchedules = scheduleRepository.findConflictingSchedules(startDateTime, endDateTime);

        List<Trainer> availableTrainers = allTrainers.stream()
                .filter(trainer -> conflictingSchedules.stream()
                        .noneMatch(schedule -> schedule.getTrainer().getId().equals(trainer.getId())))
                .collect(Collectors.toList());

        return availableTrainers.stream()
                .map(Trainer::getDto)
                .collect(Collectors.toList());
    }


}
