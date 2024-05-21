package com.mrd.server.services.impl;

import com.mrd.server.dto.CategoryDto;
import com.mrd.server.dto.TrainerDto;
import com.mrd.server.models.Category;
import com.mrd.server.models.Trainer;
import com.mrd.server.repositories.TrainerRepository;
import com.mrd.server.services.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerDto createTrainer(TrainerDto trainerDto)throws IOException {
        Trainer trainer = new Trainer();
        trainer.setFirstName(trainerDto.getFirstName());
        trainer.setLastName(trainerDto.getLastName());
        trainer.setEmail(trainerDto.getEmail());
        trainer.setPhoneNumber(trainerDto.getPhoneNumber());
        trainer.setAddress(trainerDto.getAddress());
        trainer.setCity(trainerDto.getCity());

        byte[] imageData = trainerDto.getProfilePictureFile().getBytes();
        trainer.setProfilePicture(imageData);
        return trainerRepository.save(trainer).getDto();
    }

    public List<TrainerDto> getAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        return trainers.stream().map(Trainer::getDto).collect(Collectors.toList());
    }

    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }

    public TrainerDto updateTrainer(TrainerDto trainerDto) throws IOException {
        Trainer trainer = trainerRepository.findById(trainerDto.getId())
                .orElseThrow();
        System.out.println(trainer);
        trainer.setFirstName(trainerDto.getFirstName());
        trainer.setLastName(trainerDto.getLastName());
        trainer.setEmail(trainerDto.getEmail());
        trainer.setPhoneNumber(trainerDto.getPhoneNumber());
        trainer.setAddress(trainerDto.getAddress());
        trainer.setCity(trainerDto.getCity());

        if (trainerDto.getProfilePictureFile() != null) {
            byte[] imageData = trainerDto.getProfilePictureFile().getBytes();
            trainer.setProfilePicture(imageData);
        }

        trainerRepository.save(trainer);

        return trainer.getDto();
    }

    public List<TrainerDto> getTrainersByName(String name) {
        List<Trainer> trainers = trainerRepository.findAllByFirstNameContainingIgnoreCase(name);
        return trainers.stream().map(Trainer::getDto).collect(Collectors.toList());
    }
}
