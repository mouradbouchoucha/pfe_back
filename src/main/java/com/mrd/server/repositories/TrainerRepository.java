package com.mrd.server.repositories;

import com.mrd.server.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer,Long> {
    List<Trainer> findAllByFirstNameContainingIgnoreCase(String name);

    Optional<Trainer> findByEmail(String email);

    List<Trainer> findAllByOrderByCreatedAtDesc();
}
