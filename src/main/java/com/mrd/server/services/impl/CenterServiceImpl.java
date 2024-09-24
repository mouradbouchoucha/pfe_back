package com.mrd.server.services.impl;

import com.mrd.server.dto.CenterDto;
import com.mrd.server.models.Center;
import com.mrd.server.repositories.CenterRepository;
import com.mrd.server.services.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;
    @Override
    public CenterDto getCenter() {
        Optional<Center> optionalCenter = centerRepository.findById(1L);
        return optionalCenter.get().getDto();
    }

    @Override
    public CenterDto createCenter(CenterDto centerDto) {
        return null;
    }

    @Override
    public CenterDto updateCenter(CenterDto centerDto) {
        return null;
    }


}
