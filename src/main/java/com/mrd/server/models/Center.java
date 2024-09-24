package com.mrd.server.models;

import com.mrd.server.dto.CenterDto;
import jakarta.persistence.*;
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
    @Lob
    @Column(columnDefinition = "LONGBLOB", length = 1000000000)
    private byte[] image;

    public CenterDto getDto() {
        CenterDto centerDto = new CenterDto();
        centerDto.setId(id);
        centerDto.setName(name);
        centerDto.setDescription(description);
        centerDto.setLocation(location);
        centerDto.setImage(image);
        return centerDto;
    }
}
