package com.mrd.server.services.impl;

import com.mrd.server.dto.TraineeDto;
import com.mrd.server.models.Course;
import com.mrd.server.models.Trainee;
import com.mrd.server.repositories.CourseRepository;
import com.mrd.server.repositories.TraineeRepository;
import com.mrd.server.services.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final CourseRepository courseRepository;

    public TraineeDto createTrainee(TraineeDto traineeDto)throws IOException {
        Trainee trainee = new Trainee();
        trainee.setFirstName(traineeDto.getFirstName());
        trainee.setLastName(traineeDto.getLastName());
        trainee.setEmail(traineeDto.getEmail());
        trainee.setProfession(traineeDto.getProfession());
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
        trainee.setProfession(traineeDto.getProfession());
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

    @Override
    public TraineeDto enrollInCourse(Long traineeId, Long courseId) {
        Optional<Trainee> traineeOptional = traineeRepository.findById(traineeId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (traineeOptional.isPresent() && courseOptional.isPresent()) {
            Trainee trainee = traineeOptional.get();
            Course course = courseOptional.get();
            trainee.getEnrolledCourses().add(course);
            trainee = traineeRepository.save(trainee);

            course.setEnrollmentCount(course.getEnrollmentCount() + 1);
            return trainee.getDto();
        }
        return null;
    }

    @Override
    public TraineeDto likeCourse(Long traineeId, Long courseId) {
        Optional<Trainee> traineeOptional = traineeRepository.findById(traineeId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (traineeOptional.isPresent() && courseOptional.isPresent()) {
            Trainee trainee = traineeOptional.get();
            Course course = courseOptional.get();
            trainee.getLikedCourses().add(course);
            trainee = traineeRepository.save(trainee);
            return trainee.getDto();
        }
        return null;
    }
}
