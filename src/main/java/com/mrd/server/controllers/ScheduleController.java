package com.mrd.server.controllers;

import com.mrd.server.dto.ScheduleDto;
import com.mrd.server.dto.TrainerDto;
import com.mrd.server.repositories.ScheduleRepository;
import com.mrd.server.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    @PostMapping("/create")
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        ScheduleDto createdSchedule = scheduleService.createSchedule(scheduleDto);
        return ResponseEntity.ok(createdSchedule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable Long id) {
        ScheduleDto schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
        List<ScheduleDto> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto) {
        ScheduleDto updatedSchedule = scheduleService.updateSchedule(id, scheduleDto);
        return ResponseEntity.ok(updatedSchedule);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ScheduleDto>> getScheduleByCourseId(@PathVariable Long courseId) {
        List<ScheduleDto> schedules = scheduleService.getScheduleByCourseId(courseId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<ScheduleDto>> getScheduleByTrainerId(@PathVariable Long trainerId) {
        List<ScheduleDto> schedules = scheduleService.getScheduleByTrainerId(trainerId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/trainer/{trainerId}/course/{courseId}")
    public ResponseEntity<List<ScheduleDto>> getScheduleByTrainerIdAndCourseId(
            @PathVariable Long trainerId, @PathVariable Long courseId) {
        List<ScheduleDto> schedules = scheduleService.getScheduleByTrainerIdAndCourseId(trainerId, courseId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/startDateTime")
    public ResponseEntity<ScheduleDto> getScheduleByStartDateTime(@RequestParam LocalDateTime startDateTime) {
        ScheduleDto schedule = scheduleService.getScheduleByStartDateTime(startDateTime);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/exist")
    public ResponseEntity<Boolean> exist(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,Long courseId) {
        boolean exist = scheduleRepository.existsByStartDateTimeAndCourseId(startDateTime, courseId);
        return ResponseEntity.ok(exist);
    }

    @GetMapping("/available-trainers")
    public List<TrainerDto> getAvailableTrainers(
            @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam("duration") double duration) {
        return scheduleService.getAvailableTrainers(startDateTime, duration);
    }
}
