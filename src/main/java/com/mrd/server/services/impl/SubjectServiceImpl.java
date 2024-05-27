package com.mrd.server.services.impl;

import com.mrd.server.dto.SubjectDto;
import com.mrd.server.models.Subject;
import com.mrd.server.repositories.SubjectRepository;
import com.mrd.server.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    @Override
    public SubjectDto createSubject(SubjectDto subject) {
        Subject subject1 = new Subject();
        BeanUtils.copyProperties(subject, subject1);
        return subjectRepository.save(subject1).getDto();
    }

    @Override
    public SubjectDto getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id).orElse(null);
        assert subject != null;
        return subject.getDto();
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().map(Subject::getDto).collect(Collectors.toList());
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public SubjectDto updateSubject(Long id, SubjectDto subject) {
        Subject existingSubject = subjectRepository.findById(id).orElse(null);
        if (existingSubject == null) {
            return null; // or throw an exception
        }
        BeanUtils.copyProperties(subject, existingSubject);
        return subjectRepository.save(existingSubject).getDto();
    }
}
