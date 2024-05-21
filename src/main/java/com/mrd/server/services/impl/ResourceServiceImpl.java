package com.mrd.server.services.impl;

import com.mrd.server.dto.ResourceDto;
import com.mrd.server.dto.ScheduleDto;
import com.mrd.server.models.Resource;
import com.mrd.server.models.Schedule;
import com.mrd.server.repositories.CourseRepository;
import com.mrd.server.repositories.ResourceRepository;
import com.mrd.server.repositories.ScheduleRepository;
import com.mrd.server.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;


    @Override
    public ResourceDto createResource(ResourceDto resourceDTO) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceDTO, resource);
        return resourceRepository.save(resource).getDto();
    }

    @Override
    public ResourceDto getResourceById(Long id) {
        return convertToDTO(resourceRepository.findById(id).orElse(null));
    }

    @Override
    public List<ResourceDto> getAllResources() {
        return resourceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public ResourceDto updateResource(Long id, ResourceDto resourceDTO) {
        Resource existingResource = resourceRepository.findById(id).orElse(null);
        if (existingResource == null) {
            return null; // or throw an exception
        }
        BeanUtils.copyProperties(resourceDTO, existingResource);
        return convertToDTO(resourceRepository.save(existingResource));
    }

    private ResourceDto convertToDTO(Resource resource) {
        ResourceDto resourceDTO = new ResourceDto();
        BeanUtils.copyProperties(resource, resourceDTO);
        return resourceDTO;
    }
}
