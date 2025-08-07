package com.project.filehandling.Repository;

import com.project.filehandling.Model.NewFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewFileEntityRepository extends JpaRepository<NewFileEntity,Long> {
}
