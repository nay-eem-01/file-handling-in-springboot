package com.project.filehandling.Controller;

import com.project.filehandling.Model.FileEntity;
import com.project.filehandling.ResponseData;
import com.project.filehandling.Service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {


    private final FileService fileService;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        FileEntity savedFile = fileService.uploadFile(file);

        String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile/")
                .path(savedFile.getFileId().toString())
                .toUriString();


        return new ResponseData(savedFile.getFileId(), savedFile.getFileName(), savedFile.getFileType(), savedFile.getFileSize(), downloadURl);

    }


    @GetMapping("/downloadFile/{fileId}")
    ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        try {
            ResponseData responseData = fileService.downloadFile(fileId);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(responseData.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + responseData.getFileName() + "\"")
                    .body(responseData.getResource());
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
