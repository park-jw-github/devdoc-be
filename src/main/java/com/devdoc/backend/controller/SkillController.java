package com.devdoc.backend.controller;

import com.devdoc.backend.dto.SkillDTO;
import com.devdoc.backend.service.SkillService;
import com.devdoc.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    // Skill ID 조회 : 컨테이너 ID 부여용
    @GetMapping("/{resumeId}")
    public ResponseEntity<List<Integer>> getSkillIdByResumeId(@PathVariable int resumeId) {
        try {
            List<Integer> skillIds = skillService.getSkillIdByResumeId(resumeId);
            return ResponseEntity.ok(skillIds);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Skill 조회 : 모든 테이블 & IF Empty ~ Skill x3 생성 ------------------------ Test Code
    @GetMapping("/test/{resumeId}/data")
    public ResponseEntity<List<SkillDTO>> getSkillByResumeIdTest(@PathVariable int resumeId) {
        try {
            List<SkillDTO> skills = skillService.getSkillByResumeIdTest(resumeId);
            if (skills.isEmpty()) {
                skillService.createSkillByResumeId(resumeId);
            }
            return ResponseEntity.ok(skills);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Skill 조회 : Status = T 인 모든 테이블 & IF Empty ~ Skill x3 생성
    @GetMapping("/{resumeId}/data")
    public ResponseEntity<List<SkillDTO>> getSkillByResumeId(@PathVariable int resumeId) {
        try {
            List<SkillDTO> skills = skillService.getSkillByResumeId(resumeId);
            if (skills.isEmpty()) {
                skillService.createSkillByResumeId(resumeId);
            }
            return ResponseEntity.ok(skills);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Skill 삭제 : Skill 전체
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteSkillByResumeId(@PathVariable int resumeId) {
        try {
            skillService.deleteSkillByResumeId(resumeId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
