package com.project.filehandling.Service;

import com.project.filehandling.Model.FileEntity;
import com.project.filehandling.ResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface FileService {
    FileEntity uploadFile(MultipartFile file) throws IOException;
    ResponseData downloadFile(Long fileId) throws IOException;

    Optional<FileEntity> getFileByFileName(String FileName);

}
