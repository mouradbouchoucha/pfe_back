package com.mrd.server.services;

import com.mrd.server.dto.SubjectDto;
import com.mrd.server.models.Subject;

import java.util.List;

public interface SubjectService {

  SubjectDto createSubject(SubjectDto subject);
    SubjectDto getSubjectById(Long id);
  List<SubjectDto> getAllSubjects();
  void deleteSubject(Long id);
    SubjectDto updateSubject(Long id, SubjectDto subject);
}
