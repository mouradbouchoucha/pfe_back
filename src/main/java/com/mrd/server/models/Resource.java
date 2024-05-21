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
    private String type;
    private boolean available;
    private String url;



    @ManyToMany
    @JoinTable(
            name = "schedule_resource",
            joinColumns = @JoinColumn(name = "resource_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private List<Schedule> schedules;

    public ResourceDto getDto(){
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId(id);
        resourceDto.setName(name);
        resourceDto.setType(type);
        resourceDto.setAvailable(available);
        resourceDto.setUrl(url);
        return resourceDto;
    }
}
