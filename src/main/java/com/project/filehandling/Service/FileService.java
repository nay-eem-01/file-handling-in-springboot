package com.project.filehandling.Service;

import com.project.filehandling.Model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface FileService {
    FileEntity uploadFile(MultipartFile file) throws IOException;

    Optional<FileEntity> getFile(String FileName);
}
