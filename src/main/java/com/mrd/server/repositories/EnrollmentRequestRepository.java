package com.mrd.server.repositories;

import com.mrd.server.models.Course;
import com.mrd.server.models.EnrollmentRequest;
import com.mrd.server.models.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRequestRepository extends JpaRepository<EnrollmentRequest, Long> {

    List<EnrollmentRequest> findByStatus(EnrollmentRequest.Status status);
    List<EnrollmentRequest> findByCourseIdAndStatus(Long courseId, EnrollmentRequest.Status status);
    List<EnrollmentRequest> findByTraineeId(Long traineeId);
    Optional<EnrollmentRequest> findByTraineeAndCourse(Trainee trainee, Course course);

    boolean existsByTraineeIdAndCourseId(Long traineeId, Long courseId);
}
