package com.mrd.server.controllers;

import com.mrd.server.dto.CenterDto;
import com.mrd.server.services.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin("*")
@RequestMapping("/api/center")
@RequiredArgsConstructor
public class CenterController {
    private final CenterService centerService;

    @RequestMapping
    public CenterDto get() {
        return centerService.getCenter();
    }
}
