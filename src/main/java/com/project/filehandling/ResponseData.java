package com.project.filehandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    private Long fileId;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String downloadURL;
}
