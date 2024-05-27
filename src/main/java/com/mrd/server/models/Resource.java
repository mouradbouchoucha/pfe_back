package com.mrd.server.models;

import com.mrd.server.dto.ResourceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private boolean available;





    public ResourceDto getDto(){
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId(id);
        resourceDto.setName(name);
        resourceDto.setLocation(location);
        resourceDto.setAvailable(available);
        return resourceDto;
    }
}
