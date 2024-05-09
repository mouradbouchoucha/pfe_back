package com.mrd.server.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Lob
    @Column(columnDefinition = "LONGBLOB",length = 1000000000)
    private byte[] content;
}
