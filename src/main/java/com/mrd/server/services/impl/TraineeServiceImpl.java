package com.mrd.server.services.impl;

import com.mrd.server.dto.TraineeDto;
import com.mrd.server.models.Trainee;
import com.mrd.server.repositories.TraineeRepository;
import com.mrd.server.services.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;

    public TraineeDto createTrainee(TraineeDto traineeDto)throws IOException {
        Trainee trainee = new Trainee();
        trainee.setFirstName(traineeDto.getFirstName());
        trainee.setLastName(traineeDto.getLastName());
        trainee.setEmail(traineeDto.getEmail());
        trainee.setPhoneNumber(traineeDto.getPhoneNumber());
        trainee.setAddress(traineeDto.getAddress());
        trainee.setCity(traineeDto.getCity());

        byte[] imageData = traineeDto.getProfilePictureFile().getBytes();
        trainee.setProfilePicture(imageData);
        return traineeRepository.save(trainee).getDto();
    }

    public List<TraineeDto> getAllTrainees() {
        List<Trainee> trainees = traineeRepository.findAll();
        return trainees.stream().map(Trainee::getDto).collect(Collectors.toList());
    }

    public void deleteTrainee(Long id) {
        traineeRepository.deleteById(id);
    }

    public TraineeDto updateTrainee(TraineeDto traineeDto) throws IOException {
        Trainee trainee = traineeRepository.findById(traineeDto.getId())
                .orElseThrow();
        System.out.println(trainee);
        trainee.setFirstName(traineeDto.getFirstName());
        trainee.setLastName(traineeDto.getLastName());
        trainee.setEmail(traineeDto.getEmail());
        trainee.setPhoneNumber(traineeDto.getPhoneNumber());
        trainee.setAddress(traineeDto.getAddress());
        trainee.setCity(traineeDto.getCity());

        if (traineeDto.getProfilePictureFile() != null) {
            byte[] imageData = traineeDto.getProfilePictureFile().getBytes();
            trainee.setProfilePicture(imageData);
        }

        traineeRepository.save(trainee);

        return trainee.getDto();
    }

    public List<TraineeDto> getTraineesByName(String name) {
        List<Trainee> trainees = traineeRepository.findAllByFirstNameContainingIgnoreCase(name);
        return trainees.stream().map(Trainee::getDto).collect(Collectors.toList());
    }
}
