package com.mrd.server.repositories;

import com.mrd.server.models.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    Optional<Trainee> findByEmail(String email);

    List<Trainee> findAllByFirstNameContainingIgnoreCase(String name);

    List<Trainee> findAllByOrderByCreatedAtDesc();

}
