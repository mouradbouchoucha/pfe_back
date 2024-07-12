package com.mrd.server.controllers;

import com.mrd.server.dto.TrainerDto;
import com.mrd.server.models.Trainer;
import com.mrd.server.repositories.TrainerRepository;
import com.mrd.server.services.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;
    private final TrainerRepository trainerRepository;
    private static final Logger logger = LoggerFactory.getLogger(TrainerController.class);

    @GetMapping("/all")
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        List<TrainerDto> trainers = trainerService.getAllTrainersByOrderCreatedAt();
        return ResponseEntity.ok().body(trainers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getTrainerById(@PathVariable Long id) {
        TrainerDto trainerDto = trainerService.getTrainerById(id);
        return ResponseEntity.ok().body(trainerDto);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TrainerDto> createTrainer(@ModelAttribute TrainerDto trainerDto) throws IOException {
        TrainerDto _trainerDto = trainerService.createTrainer(trainerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(_trainerDto);
    }

    @PutMapping(value = "/update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TrainerDto> updateTrainer(
            @PathVariable Long id,


        @ModelAttribute TrainerDto trainerDto
//        @RequestPart(value = "profilePictureFile", required = false) MultipartFile profilePictureFile,
//            @RequestPart("firstName") String firstName,
//            @RequestPart("lastName") String lastName,
//            @RequestPart("institutionName") String institutionName,
//            @RequestPart("departmentName") String departmentName,
//            @RequestPart("yearsOfExperience") Integer yearsOfExperience,
//            @RequestPart("degree") String degree,
//            @RequestPart("phoneNumber") String phoneNumber,
//            @RequestPart("address") String address,
//            @RequestPart("city") String city
    ) throws IOException {
        Optional<Trainer> _trainer = trainerRepository.findById(id);
//        TrainerDto trainerDto = new TrainerDto();
//        trainerDto.setId(id);
//        trainerDto.setFirstName(firstName);
//        trainerDto.setLastName(lastName);
//        trainerDto.setEmail(_trainer.get().getEmail());
//        trainerDto.setInstitutionName(institutionName);
//        trainerDto.setDepartmentName(departmentName);
//        trainerDto.setYearsOfExperience(yearsOfExperience);
//        trainerDto.setDegree(degree);
//        trainerDto.setPhoneNumber(phoneNumber);
//        trainerDto.setAddress(address);
//        trainerDto.setCity(city);
//        trainerDto.setProfilePictureFile(profilePictureFile);
        TrainerDto _trainerDto = new TrainerDto();
        _trainerDto.setId(trainerDto.getId());
        _trainerDto.setFirstName(trainerDto.getFirstName());
        _trainerDto.setLastName(trainerDto.getLastName());
        _trainerDto.setEmail(_trainer.get().getEmail());
        _trainerDto.setInstitutionName(trainerDto.getInstitutionName());
        _trainerDto.setDepartmentName(trainerDto.getDepartmentName());
        _trainerDto.setYearsOfExperience(trainerDto.getYearsOfExperience());
        _trainerDto.setDegree(trainerDto.getDegree());
        _trainerDto.setPhoneNumber(trainerDto.getPhoneNumber());
        _trainerDto.setAddress(trainerDto.getAddress());
        _trainerDto.setCity(trainerDto.getCity());
        _trainerDto.setProfilePictureFile(trainerDto.getProfilePictureFile());

        TrainerDto updatedTrainer = trainerService.updateTrainer(_trainerDto);
        return ResponseEntity.ok().body(updatedTrainer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
    }



    @GetMapping("/search/{name}")
    public ResponseEntity<List<TrainerDto>> getTrainersByName(@PathVariable String name) {
        List<TrainerDto> trainersDtos = trainerService.getTrainersByName(name);
        return ResponseEntity.ok().body(trainersDtos);
    }
}
