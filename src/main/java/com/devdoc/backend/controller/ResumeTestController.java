package com.devdoc.backend.controller;

import com.devdoc.backend.dto.ResumeDTO;
import com.devdoc.backend.service.ResumeService;
import com.devdoc.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes/test") // ------------------------------------------ Test Code
public class ResumeTestController {

    @Autowired
    private ResumeService resumeService;

    // Resume 조회 : 모든 테이블
    @GetMapping("/{resumeId}")
    public ResponseEntity<ResumeDTO> getResumeByResumeIdTest(@PathVariable int resumeId) {
        try {
            ResumeDTO resumeDTO = resumeService.getResumeByResumeIdTest(resumeId);
            return ResponseEntity.ok(resumeDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Resume 생성 : User ID = 1
    @PostMapping("")
    public ResponseEntity<ResumeDTO> createResumeTest(@RequestParam String title) {
        try {
            ResumeDTO resumeDTO = resumeService.createResumeTest(title);
            return ResponseEntity.status(HttpStatus.CREATED).body(resumeDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Resume 삭제
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResumeTest(@PathVariable int resumeId) {
        try {
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
    public ResponseEntity<ResumeDTO> saveResumeTitleByResumeIdTest(@PathVariable int resumeId, @RequestParam String title) {
        try {
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
    public ResponseEntity<ResumeDTO> saveResumeDataByResumeIdTest(@PathVariable int resumeId, @RequestBody ResumeDTO resumeDTO) {
        try {
            ResumeDTO updatedResumeDTO = resumeService.saveResumeDataByResumeId(resumeId, resumeDTO);
            return ResponseEntity.ok(updatedResumeDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}