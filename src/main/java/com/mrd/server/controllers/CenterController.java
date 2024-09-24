package com.mrd.server.controllers;

import com.mrd.server.dto.CenterDto;
import com.mrd.server.services.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/centers")
@RequiredArgsConstructor
public class CenterController {
    private final CenterService centerService;

    @GetMapping("/center")
    public ResponseEntity<CenterDto> get() {
        System.out.println("Received request for /api/center/center");
        CenterDto center = centerService.getCenter();
        return ResponseEntity.ok(center);
    }

    @GetMapping("/test")
    public String test() {

        return "Test successful";
    }
}
