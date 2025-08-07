package com.project.filehandling.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String fileName;
    private String fileType;
    private Long fileSize;



    @Lob
    private byte [] data;

    public NewFileEntity(String fileName, String fileType, Long fileSize, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.data = data;
    }
}
