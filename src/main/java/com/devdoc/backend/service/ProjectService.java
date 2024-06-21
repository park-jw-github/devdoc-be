package com.devdoc.backend.service;

import com.devdoc.backend.dto.ProjectDTO;
import com.devdoc.backend.model.Project;
import com.devdoc.backend.model.Resume;
import com.devdoc.backend.repository.ProjectRepository;
import com.devdoc.backend.repository.ResumeRepository;
import com.devdoc.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    // Project ID 조회 : 컨테이너 ID 부여용
    public List<Integer> getProjectIdByResumeId(int resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        return resume.getProjects().stream()
                .map(Project::getId)
                .collect(Collectors.toList());
    }

    // Project 조회 : 모든 테이블 ------------------------------------------------- Test Code
    public List<ProjectDTO> getProjectByResumeIdTest(int resumeId) {
        return fetchProjectsForResume(resumeId);
    }

    // Project 조회 : Status = T 인 모든 테이블
    public List<ProjectDTO> getProjectByResumeId(int resumeId) {
        return fetchProjectsForResume(resumeId).stream()
                .filter(ProjectDTO::isStatus)
                .collect(Collectors.toList());
    }

    // Project 생성 : Resume ~ Project x3
    public void createProjectByResumeId(int resumeId) {
        List<Integer> projectIds = getProjectIdByResumeId(resumeId);
    
        if (projectIds.size() >= 3) {
            throw new IllegalStateException("Cannot create more than 3 projects for a resume.");
        }
    
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
    
        for (int i = projectIds.size(); i < 3; i++) {
            Project project = new Project(resume, false, null, null, null, null, null, null);
            projectRepository.save(project);
        }
    
        resumeRepository.flush();
    }

    // Project 삭제 : Project 전체
    public void deleteProjectByResumeId(int resumeId) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new ResourceNotFoundException("Resume not found");
        }

        projectRepository.deleteByResumeId(resumeId);
    }

    // -------------------------------------------------------------------------------------
    // 호출함수
    // -------------------------------------------------------------------------------------

    private List<ProjectDTO> fetchProjectsForResume(int resumeId) {
        return projectRepository.findByResumeId(resumeId).stream()
                .map(project -> new ProjectDTO(
                        project.getId(), 
                        project.getResume().getId(), 
                        project.isStatus(), 
                        project.getTitle(), 
                        project.getPeriod(), 
                        project.getIsCurrent(), 
                        project.getIntro(), 
                        project.getTechStack(), 
                        project.getContent())
                )
                .collect(Collectors.toList());
    }
}
