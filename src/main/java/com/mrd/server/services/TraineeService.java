package com.mrd.server.services;

import com.mrd.server.dto.TraineeDto;


import java.io.IOException;
import java.util.List;

public interface TraineeService {

    TraineeDto createTrainee(TraineeDto traineeDto)throws IOException;
    List<TraineeDto> getAllTrainees();
    void deleteTrainee(Long id);
    TraineeDto updateTrainee(TraineeDto traineeDto) throws IOException;
    List<TraineeDto> getTraineesByName(String name);

    TraineeDto enrollInCourse(Long traineeId, Long courseId);

    TraineeDto likeCourse(Long traineeId, Long courseId);

    List<TraineeDto> getTraineesSortedByCreatedAt();

    TraineeDto getTraineeByEmail(String email);

    TraineeDto getTraineeById(Long id);

}
