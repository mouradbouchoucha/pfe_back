package com.mrd.server.controllers;

import com.mrd.server.dto.TrainerDto;
import com.mrd.server.models.Trainer;
import com.mrd.server.repositories.TrainerRepository;
import com.mrd.server.services.TrainerService;
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
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;
    private final TrainerRepository trainerRepository;

    @GetMapping("/all")
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        List<TrainerDto> trainers = trainerService.getAllTrainers();
        return ResponseEntity.ok().body(trainers);
    }

    @PostMapping("/create")
    public ResponseEntity<TrainerDto> createTrainer(
            @ModelAttribute TrainerDto trainerDto
    ) throws IOException {
        TrainerDto _trainerDto = trainerService.createTrainer(trainerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(_trainerDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TrainerDto> updateTrainer(
            @PathVariable Long id,
            @RequestPart(value = "profilePictureFile", required = false) MultipartFile profilePictureFile,
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("phoneNumber") String phoneNumber,
            @RequestPart("address") String address,
            @RequestPart("city") String city
    ) throws IOException {
        Optional<Trainer> _trainer = trainerRepository.findById(id);
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setId(id);
        trainerDto.setFirstName(firstName);
        trainerDto.setLastName(lastName);
        trainerDto.setEmail(_trainer.get().getEmail());
        trainerDto.setPhoneNumber(phoneNumber);
        trainerDto.setAddress(address);
        trainerDto.setCity(city);
        trainerDto.setProfilePictureFile(profilePictureFile);

        TrainerDto updatedTrainer = trainerService.updateTrainer(trainerDto);
        return ResponseEntity.ok().body(updatedTrainer);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<TrainerDto>> getTrainersByName(@PathVariable String name) {
        List<TrainerDto> trainersDtos = trainerService.getTrainersByName(name);
        return ResponseEntity.ok().body(trainersDtos);
    }
}
