package com.mrd.server.services;

import com.mrd.server.dto.CenterDto;

import java.util.List;

public interface CenterService {

    CenterDto getCenter();

    CenterDto createCenter(CenterDto centerDto);

    CenterDto updateCenter(CenterDto centerDto);



}
