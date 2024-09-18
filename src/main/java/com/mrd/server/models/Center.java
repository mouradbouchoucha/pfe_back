package com.mrd.server.models;

import com.mrd.server.dto.CenterDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String location;

    public CenterDto getDto() {
        CenterDto centerDto = new CenterDto();
        centerDto.setId(id);
        centerDto.setName(name);
        centerDto.setDescription(description);
        centerDto.setLocation(location);

        return centerDto;
    }
}
