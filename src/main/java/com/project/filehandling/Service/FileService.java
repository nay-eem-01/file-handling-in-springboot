package com.project.filehandling.Service;

import com.project.filehandling.Model.FileEntity;
import com.project.filehandling.Model.NewFileEntity;
import com.project.filehandling.ResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface FileService {
    FileEntity uploadFile(MultipartFile file) throws IOException;
    ResponseData getFile(Long fileId) throws IOException;

    NewFileEntity uploadFileToDB(MultipartFile file) throws Exception;
    NewFileEntity getFileFromDB(Long fileId) throws IOException;


    Optional<FileEntity> getFileByFileName(String FileName);

}
