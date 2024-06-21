package com.devdoc.backend.repository;

import com.devdoc.backend.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CareerRepository extends JpaRepository<Career, Integer> {
    List<Career> findByResumeId(int resumeId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Career s WHERE s.resume.id = :resumeId")
    void deleteByResumeId(@Param("resumeId") int resumeId);
}
