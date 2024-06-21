package com.devdoc.backend.controller;

import com.devdoc.backend.dto.ProjectDTO;
import com.devdoc.backend.service.ProjectService;
import com.devdoc.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Project ID 조회 : 컨테이너 ID 부여용
    @GetMapping("/{resumeId}")
    public ResponseEntity<List<Integer>> getProjectIdByResumeId(@PathVariable int resumeId) {
        try {
            List<Integer> projectIds = projectService.getProjectIdByResumeId(resumeId);
            return ResponseEntity.ok(projectIds);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Project 조회 : 모든 테이블 & IF Empty ~ Project x3 생성 -------------------- Test Code
    @GetMapping("/test/{resumeId}/data")
    public ResponseEntity<List<ProjectDTO>> getProjectByResumeIdTest(@PathVariable int resumeId) {
        try {
            List<ProjectDTO> projects = projectService.getProjectByResumeIdTest(resumeId);
            if (projects.isEmpty()) {
                projectService.createProjectByResumeId(resumeId);
            }
            return ResponseEntity.ok(projects);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Project 조회 : Status = T 인 모든 테이블 & IF Empty ~ Project x3 생성
    @GetMapping("/{resumeId}/data")
    public ResponseEntity<List<ProjectDTO>> getProjectByResumeId(@PathVariable int resumeId) {
        try {
            List<ProjectDTO> projects = projectService.getProjectByResumeId(resumeId);
            if (projects.isEmpty()) {
                projectService.createProjectByResumeId(resumeId);
            }
            return ResponseEntity.ok(projects);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Project 삭제 : Project 전체
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteProjectByResumeId(@PathVariable int resumeId) {
        try {
            projectService.deleteProjectByResumeId(resumeId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
