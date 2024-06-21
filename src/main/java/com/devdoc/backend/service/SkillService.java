package com.devdoc.backend.service;

import com.devdoc.backend.dto.SkillDTO;
import com.devdoc.backend.model.Resume;
import com.devdoc.backend.model.Skill;
import com.devdoc.backend.repository.ResumeRepository;
import com.devdoc.backend.repository.SkillRepository;
import com.devdoc.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    // Skill ID 조회 : 컨테이너 ID 부여용
    public List<Integer> getSkillIdByResumeId(int resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        return resume.getSkills().stream()
                .map(Skill::getId)
                .collect(Collectors.toList());
    }

    // Skill 조회 : 모든 테이블 --------------------------------------------------- Test Code
    public List<SkillDTO> getSkillByResumeIdTest(int resumeId) {
        return fetchSkillsForResume(resumeId);
    }

    // Skill 조회 : Status = T 인 모든 테이블
    public List<SkillDTO> getSkillByResumeId(int resumeId) {
        return fetchSkillsForResume(resumeId).stream()
                .filter(SkillDTO::isStatus)
                .collect(Collectors.toList());
    }

    // Skill 생성 : Resume ~ Skill x3
    public void createSkillByResumeId(int resumeId) {
        List<Integer> skillIds = getSkillIdByResumeId(resumeId);
    
        if (skillIds.size() >= 3) {
            throw new IllegalStateException("Cannot create more than 3 skills for a resume.");
        }
    
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
    
        for (int i = skillIds.size(); i < 3; i++) {
            Skill skill = new Skill(resume, false, null, null);
            skillRepository.save(skill);
        }
    
        resumeRepository.flush();
    }

    // Skill 삭제 : Skill 전체
    public void deleteSkillByResumeId(int resumeId) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new ResourceNotFoundException("Resume not found");
        }

        skillRepository.deleteByResumeId(resumeId);
    }

    // -------------------------------------------------------------------------------------
    // 호출함수
    // -------------------------------------------------------------------------------------

    private List<SkillDTO> fetchSkillsForResume(int resumeId) {
        return skillRepository.findByResumeId(resumeId).stream()
                .map(skill -> new SkillDTO(
                        skill.getId(), 
                        skill.getResume().getId(), 
                        skill.isStatus(), 
                        skill.getTechStack(), 
                        skill.getContent())
                )
                .collect(Collectors.toList());
    }
}
