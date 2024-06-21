package com.devdoc.backend.service;

import com.devdoc.backend.dto.CareerDTO;
import com.devdoc.backend.model.Career;
import com.devdoc.backend.model.Resume;
import com.devdoc.backend.repository.CareerRepository;
import com.devdoc.backend.repository.ResumeRepository;
import com.devdoc.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    // Career ID 조회 : 컨테이너 ID 부여용
    public List<Integer> getCareerIdByResumeId(int resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        return resume.getCareers().stream()
                .map(Career::getId)
                .collect(Collectors.toList());
    }

    // Career 조회 : 모든 테이블 -------------------------------------------------- Test Code
    public List<CareerDTO> getCareerByResumeIdTest(int resumeId) {
        return fetchCareersForResume(resumeId);
    }

    // Career 조회 : Status = T 인 모든 테이블
    public List<CareerDTO> getCareerByResumeId(int resumeId) {
        return fetchCareersForResume(resumeId).stream()
                .filter(CareerDTO::isStatus)
                .collect(Collectors.toList());
    }

    // Career 생성 : Resume ~ Career x3
    public void createCareerByResumeId(int resumeId) {
        List<Integer> careerIds = getCareerIdByResumeId(resumeId);
    
        if (careerIds.size() >= 3) {
            throw new IllegalStateException("Cannot create more than 3 careers for a resume.");
        }
    
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
    
        for (int i = careerIds.size(); i < 3; i++) {
            Career career = new Career(resume, false, null, null, null, null, null, null);
            careerRepository.save(career);
        }
    
        resumeRepository.flush();
    }

    // Career 삭제 : Career 전체
    public void deleteCareerByResumeId(int resumeId) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new ResourceNotFoundException("Resume not found");
        }

        careerRepository.deleteByResumeId(resumeId);
    }

    // -------------------------------------------------------------------------------------
    // 호출함수
    // -------------------------------------------------------------------------------------

    private List<CareerDTO> fetchCareersForResume(int resumeId) {
        return careerRepository.findByResumeId(resumeId).stream()
                .map(career -> new CareerDTO(
                        career.getId(), 
                        career.getResume().getId(), 
                        career.isStatus(), 
                        career.getCompany(), 
                        career.getDepartment(), 
                        career.getPeriod(), 
                        career.getIsCurrent(), 
                        career.getTechStack(), 
                        career.getContent())
                )
                .collect(Collectors.toList());
    }
}
