package com.mrd.server.repositories;

import com.mrd.server.models.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    Trainee findByEmail(String email);

    List<Trainee> findAllByFirstNameContainingIgnoreCase(String name);

    List<Trainee> findAllByOrderByCreatedAtDesc();

}
