package com.mrd.server.models;

import com.mrd.server.dto.SubjectDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public SubjectDto getDto(){
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(id);
        subjectDto.setName(name);
        return subjectDto;
    }

}
