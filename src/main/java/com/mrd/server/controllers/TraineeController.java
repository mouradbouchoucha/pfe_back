package com.mrd.server.controllers;

import com.mrd.server.dto.TraineeDto;
import com.mrd.server.models.Trainee;
import com.mrd.server.models.User;
import com.mrd.server.repositories.TraineeRepository;
import com.mrd.server.services.TraineeService;
import com.mrd.server.services.UserService;
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
    private final UserService userService;
    private final TraineeRepository traineeRepository;

    @GetMapping("/all")
    public ResponseEntity<List<TraineeDto>> getAllTraineesByOrderCreatedAt() {
        List<TraineeDto> trainees = traineeService.getTraineesSortedByCreatedAt();
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
            @RequestPart("profession") String profession,
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
        traineeDto.setProfession(profession);
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

    @PostMapping("/{traineeId}/enroll/{courseId}")
    public ResponseEntity<TraineeDto> enrollInCourse(@PathVariable Long traineeId, @PathVariable Long courseId) {
        TraineeDto updatedTrainee = traineeService.enrollInCourse(traineeId, courseId);
        if (updatedTrainee != null) {
            return ResponseEntity.ok(updatedTrainee);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{traineeId}/like/{courseId}")
    public ResponseEntity<TraineeDto> likeCourse(@PathVariable Long traineeId, @PathVariable Long courseId) {
        TraineeDto updatedTrainee = traineeService.likeCourse(traineeId, courseId);
        if (updatedTrainee != null) {
            return ResponseEntity.ok(updatedTrainee);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("trainee")
    public ResponseEntity<User> getTraineeByEmail(@RequestParam String email) {
        User traineeDto = userService.findUserByEmail(email);
        Long id = traineeDto.getId();
        TraineeDto trainee = traineeService.getTraineeById(id);
        return ResponseEntity.ok().body(traineeDto);
    }
}
