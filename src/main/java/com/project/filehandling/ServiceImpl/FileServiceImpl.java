package com.project.filehandling.ServiceImpl;

import com.project.filehandling.ExceptionHandling.FileNotFoundException;
import com.project.filehandling.ExceptionHandling.FileSavingException;
import com.project.filehandling.Model.FileEntity;
import com.project.filehandling.Repository.FileRepository;
import com.project.filehandling.ResponseData;
import com.project.filehandling.Service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    private final String uploadDir = "F:\\springboot_file_upload_demo\\";

    @Override
    public FileEntity uploadFile(MultipartFile file) {

        Path uploadPath = Paths.get(uploadDir);

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = file.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            FileEntity fileEntity = new FileEntity(fileName, file.getContentType(), filePath.toString(), file.getSize());

            return fileRepository.save(fileEntity);

        } catch (IOException e) {
            throw new FileSavingException("Failed to upload file");
        }


    }





    public ResponseData downloadFile(Long fileId) {
        return fileRepository.findById(fileId)
                .map(fileEntity -> {
                    try {
                        Path path = Paths.get(fileEntity.getFilePath());
                        Resource resource = new UrlResource(path.toUri());
                        if (!resource.exists()) {
                            throw new FileNotFoundException("File not found");
                        }
                        return new ResponseData(fileId, fileEntity.getFileName(), fileEntity.getFileType(),resource);

                    } catch (MalformedURLException e) {
                        throw new RuntimeException("Failed to load file", e);
                    }
                })
                .orElseThrow(() -> new FileNotFoundException("File not found"));
    }


    @Override
    public Optional<FileEntity> getFileByFileName(String FileName) {
        return fileRepository.findByFileName(FileName);
    }




}
