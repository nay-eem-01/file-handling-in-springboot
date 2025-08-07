package com.project.filehandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    private Long fileId;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String downloadURL;
    private Resource resource;

    public ResponseData(Long fileId, String fileName, String fileType, Resource resource) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.resource = resource;
    }

    public ResponseData(Long fileId, String fileName, String fileType, Long fileSize, String downloadURL) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.downloadURL = downloadURL;
    }
}
