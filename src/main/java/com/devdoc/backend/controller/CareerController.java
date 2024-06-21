package com.devdoc.backend.controller;

import com.devdoc.backend.dto.CareerDTO;
import com.devdoc.backend.service.CareerService;
import com.devdoc.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/careers")
public class CareerController {

    @Autowired
    private CareerService careerService;

    // Career ID 조회 : 컨테이너 ID 부여용
    @GetMapping("/{resumeId}")
    public ResponseEntity<List<Integer>> getCareerIdByResumeId(@PathVariable int resumeId) {
        try {
            List<Integer> careerIds = careerService.getCareerIdByResumeId(resumeId);
            return ResponseEntity.ok(careerIds);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Career 조회 : 모든 테이블 & IF Empty ~ Career x3 생성 ---------------------- Test Code
    @GetMapping("/test/{resumeId}/data")
    public ResponseEntity<List<CareerDTO>> getCareerByResumeIdTest(@PathVariable int resumeId) {
        try {
            List<CareerDTO> careers = careerService.getCareerByResumeIdTest(resumeId);
            if (careers.isEmpty()) {
                careerService.createCareerByResumeId(resumeId);
            }
            return ResponseEntity.ok(careers);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Career 조회 : Status = T 인 모든 테이블 & IF Empty ~ Career x3 생성
    @GetMapping("/{resumeId}/data")
    public ResponseEntity<List<CareerDTO>> getCareerByResumeId(@PathVariable int resumeId) {
        try {
            List<CareerDTO> careers = careerService.getCareerByResumeId(resumeId);
            if (careers.isEmpty()) {
                careerService.createCareerByResumeId(resumeId);
            }
            return ResponseEntity.ok(careers);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Career 삭제 : Career 전체
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteCareerByResumeId(@PathVariable int resumeId) {
        try {
            careerService.deleteCareerByResumeId(resumeId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
