package com.mrd.server.controllers;

import com.mrd.server.dto.SubjectDto;
import com.mrd.server.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @RequestMapping("/create")
    public ResponseEntity<SubjectDto> createSubject(@RequestParam SubjectDto subjectDto) {
        SubjectDto createdSubject = subjectService.createSubject(subjectDto);
        return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
    }

    @GetMapping("/subject/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable Long id) {
        SubjectDto subjectDto = subjectService.getSubjectById(id);
        return subjectDto != null ? ResponseEntity.ok(subjectDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubjectDto>> getAllSubjects() {
        List<SubjectDto> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SubjectDto> updateSubject(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description
    ){
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(id);
        subjectDto.setName(name);
        return ResponseEntity.ok(subjectService.updateSubject(id, subjectDto));
    }
}
