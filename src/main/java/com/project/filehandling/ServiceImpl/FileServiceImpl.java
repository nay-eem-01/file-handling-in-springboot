package com.project.filehandling.ServiceImpl;

import com.project.filehandling.Model.FileEntity;
import com.project.filehandling.Repository.FileRepository;
import com.project.filehandling.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    private final String uploadDir="F:\\springboot_file_upload_demo\\";

    @Override
    public FileEntity uploadFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        FileEntity fileEntity = new FileEntity(fileName,file.getContentType(),filePath.toString(), file.getSize());


        return fileRepository.save(fileEntity);
    }

    @Override
    public Optional<FileEntity> getFile(String FileName) {
        return fileRepository.findByFileName(FileName);
    }


}
