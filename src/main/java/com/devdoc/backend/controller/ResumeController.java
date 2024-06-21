package com.devdoc.backend.controller;

import com.devdoc.backend.dto.ResumeDTO;
import com.devdoc.backend.service.ResumeService;
import com.devdoc.backend.service.UserService;
import com.devdoc.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private UserService userService;

    // Resume 조회 : Status = T 인 모든 테이블
    @GetMapping("/{resumeId}")
    public ResponseEntity<ResumeDTO> getResumeByResumeId(@PathVariable int resumeId) {
        try {
            if (!userService.isOwner(resumeId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            ResumeDTO resumeDTO = resumeService.getResumeByResumeId(resumeId);
            return ResponseEntity.ok(resumeDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Resume 생성 : 로그인 User
    @PostMapping
    public ResponseEntity<ResumeDTO> createResume(@RequestParam String title) {
        try {
            ResumeDTO resumeDTO = resumeService.createResume(title);
            return ResponseEntity.status(HttpStatus.CREATED).body(resumeDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Resume 삭제
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(@PathVariable int resumeId) {
        try {
            if (!userService.isOwner(resumeId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            resumeService.deleteResume(resumeId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Resume 업데이트 : Title
    @PatchMapping("/{resumeId}/title")
    public ResponseEntity<ResumeDTO> saveResumeTitleByResumeId(@PathVariable int resumeId, @RequestParam String title) {
        try {
            if (!userService.isOwner(resumeId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            ResumeDTO resumeDTO = resumeService.saveResumeTitleByResumeId(resumeId, title);
            return ResponseEntity.ok(resumeDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Resume 업데이트 : Title 포함 테이블 전체
    @PatchMapping("/{resumeId}/data")
    public ResponseEntity<ResumeDTO> saveResumeDataByResumeId(@PathVariable int resumeId, @RequestBody ResumeDTO resumeDTO) {
        try {
            if (!userService.isOwner(resumeId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            ResumeDTO updatedResumeDTO = resumeService.saveResumeDataByResumeId(resumeId, resumeDTO);
            return ResponseEntity.ok(updatedResumeDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
