package com.devdoc.backend.repository;

import com.devdoc.backend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    List<Skill> findByResumeId(int resumeId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Skill s WHERE s.resume.id = :resumeId")
    void deleteByResumeId(@Param("resumeId") int resumeId);
}
