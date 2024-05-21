package com.mrd.server.services;

import com.mrd.server.dto.ResourceDto;
import com.mrd.server.dto.ScheduleDto;

import java.util.List;

public interface ResourceService {

    ResourceDto createResource(ResourceDto resourceDTO);

    ResourceDto getResourceById(Long id);

    List<ResourceDto> getAllResources();

    void deleteResource(Long id);

    ResourceDto updateResource(Long id, ResourceDto resourceDTO);



}
