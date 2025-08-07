package com.project.filehandling.ServiceImpl;

import com.project.filehandling.ExceptionHandling.FileNotFoundException;
import com.project.filehandling.ExceptionHandling.FileSavingException;
import com.project.filehandling.Model.FileEntity;
import com.project.filehandling.Model.NewFileEntity;
import com.project.filehandling.Repository.FileRepository;
import com.project.filehandling.Repository.NewFileEntityRepository;
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
    private final NewFileEntityRepository newFileEntityRepository;

    public FileServiceImpl(FileRepository fileRepository, NewFileEntityRepository newFileEntityRepository) {
        this.fileRepository = fileRepository;
        this.newFileEntityRepository = newFileEntityRepository;
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





    public ResponseData getFile(Long fileId) {
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
    public NewFileEntity uploadFileToDB(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        try{
            if (fileName.contains("..")){
                throw new FileSavingException("Filename contains invalid path sequence "
                        + fileName);
            }
            NewFileEntity newFileEntity = new NewFileEntity(fileName,file.getContentType(), file.getSize(),file.getBytes());
            return  newFileEntityRepository.save(newFileEntity);
        }catch (IOException e){
            throw new FileSavingException("Could not save file");
        }

    }

    @Override
    public NewFileEntity getFileFromDB(Long fileId) throws IOException {
        return newFileEntityRepository.findById(fileId)
                .orElseThrow(()-> new FileNotFoundException("File not found"));
    }


    @Override
    public Optional<FileEntity> getFileByFileName(String FileName) {
        return fileRepository.findByFileName(FileName);
    }




}
