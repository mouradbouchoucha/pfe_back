package com.mrd.server.services;

import com.mrd.server.dto.TrainerDto;

import java.io.IOException;
import java.util.List;

public interface TrainerService {
    TrainerDto createTrainer(TrainerDto trainerDto)throws IOException;
    List<TrainerDto> getAllTrainers();
    void deleteTrainer(Long id);
    TrainerDto updateTrainer(TrainerDto trainerDto) throws IOException;
    List<TrainerDto> getTrainersByName(String name);

    TrainerDto getTrainerById(Long id);

    List<TrainerDto> getAllTrainersByOrderCreatedAt();
}
