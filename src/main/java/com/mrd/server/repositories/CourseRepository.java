package com.mrd.server.repositories;

import com.mrd.server.models.Category;
import com.mrd.server.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByNameContainingIgnoreCase(String name);
    @Query("SELECT c FROM Course c WHERE LOWER(c.name) LIKE %:term% OR LOWER(c.description) LIKE %:term%")
    List<Course> findAllByNameOrDescriptionContainingIgnoreCase(String term);
}
