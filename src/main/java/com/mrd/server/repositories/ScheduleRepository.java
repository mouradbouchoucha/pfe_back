package com.mrd.server.repositories;

import com.mrd.server.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByCourseId(Long courseId);
    List<Schedule> findAllByTrainerId(Long trainerId);
    List<Schedule> findByCourseIdAndTrainerId(Long courseId, Long trainerId);
    Optional<Schedule> findByStartDateTime(LocalDateTime startDateTime);

    boolean existsByStartDateTime(LocalDateTime startDateTime);
}
