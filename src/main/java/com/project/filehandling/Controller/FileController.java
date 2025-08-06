package com.project.filehandling.Controller;

import com.project.filehandling.Model.FileEntity;
import com.project.filehandling.ResponseData;
import com.project.filehandling.Service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/file")
public class FileController {


    private final FileService fileService;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<ResponseData> uploadFile(@RequestParam("file") MultipartFile file)  {
        try {
            FileEntity savedFile = fileService.uploadFile(file);

            String  downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/file/downloadFile/")
                    .path(savedFile.getFileId().toString())
                    .toUriString();



            return ResponseEntity.ok(new ResponseData(savedFile.getFileId(), savedFile.getFileName(), savedFile.getFileType(), savedFile.getFileSize(),downloadURl));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/downloadFile/{fileId}")
    ResponseEntity<?> downloadFile (@PathVariable Long fileId){

        return fileService.getFileByFileId(fileId)
                .map(fileEntity -> {
                    Path path = Paths.get(fileEntity.getFilePath());
                    Resource resource;

                    try{
                        resource = new UrlResource(path.toUri());
                        if (resource.exists()){
                            return ResponseEntity.ok()
                                    .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
                                    .header(HttpHeaders.CONTENT_DISPOSITION,
                                            "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                                    .body(resource);
                        }
                        else {
                            return ResponseEntity.notFound().build();
                        }
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }

                })
                .orElse(ResponseEntity.notFound().build());
    }

}
