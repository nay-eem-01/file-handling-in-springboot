package com.project.filehandling.Repository;

import com.project.filehandling.Model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Optional;
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByFileName(String fileName);
    Optional<FileEntity>  findByFileId(Long fileId);
}
