package com.devdoc.backend.repository;

import com.devdoc.backend.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    Optional<Resume> findById(int id);
    boolean existsById(int id);
}
