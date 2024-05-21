package com.mrd.server.controllers;

import com.mrd.server.dto.TraineeDto;
import com.mrd.server.dto.TrainerDto;
import com.mrd.server.models.Trainee;
import com.mrd.server.models.Trainer;
import com.mrd.server.repositories.TraineeRepository;
import com.mrd.server.repositories.TrainerRepository;
import com.mrd.server.services.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/trainees")
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;

    private final TraineeRepository traineeRepository;

    @GetMapping("/all")
    public ResponseEntity<List<TraineeDto>> getAllTrainees() {
        List<TraineeDto> trainees = traineeService.getAllTrainees();
        return ResponseEntity.ok().body(trainees);
    }

    @PostMapping("/create")
    public ResponseEntity<TraineeDto> createTrainee(
            @ModelAttribute TraineeDto traineeDto
    ) throws IOException {
        TraineeDto _traineeDto = traineeService.createTrainee(traineeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(_traineeDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTrainee(@PathVariable Long id) {
        traineeService.deleteTrainee(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TraineeDto> updateTrainee(
            @PathVariable Long id,
            @RequestPart(value = "profilePictureFile", required = false) MultipartFile profilePictureFile,
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("phoneNumber") String phoneNumber,
            @RequestPart("address") String address,
            @RequestPart("city") String city
    ) throws IOException {
        Optional<Trainee> _trainee = traineeRepository.findById(id);
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setId(id);
        traineeDto.setFirstName(firstName);
        traineeDto.setLastName(lastName);
        traineeDto.setEmail(_trainee.get().getEmail());
        traineeDto.setPhoneNumber(phoneNumber);
        traineeDto.setAddress(address);
        traineeDto.setCity(city);
        traineeDto.setProfilePictureFile(profilePictureFile);

        TraineeDto updatedTrainee = traineeService.updateTrainee(traineeDto);
        return ResponseEntity.ok().body(updatedTrainee);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<TraineeDto>> getTraineesByName(@PathVariable String name) {
        List<TraineeDto> traineesDtos = traineeService.getTraineesByName(name);
        return ResponseEntity.ok().body(traineesDtos);
    }
}
