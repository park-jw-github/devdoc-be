package com.devdoc.backend.service;

import com.devdoc.backend.dto.ResumeDTO;
import com.devdoc.backend.dto.SkillDTO;
import com.devdoc.backend.dto.CareerDTO;
import com.devdoc.backend.dto.ProjectDTO;
//
// 추가
//
import com.devdoc.backend.model.Resume;
import com.devdoc.backend.model.User;
import com.devdoc.backend.model.Skill;
import com.devdoc.backend.model.Career;
import com.devdoc.backend.model.Project;
//
// 추가
//
import com.devdoc.backend.repository.ResumeRepository;
import com.devdoc.backend.repository.SkillRepository;
import com.devdoc.backend.repository.CareerRepository;
import com.devdoc.backend.repository.ProjectRepository;
//
// 추가
//
import com.devdoc.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private ProjectRepository projectRepository;

    //
    // 추가
    //

    @Autowired
    private UserService userService;

    // Resume 조회 : 모든 테이블 -------------------------------------------------- Test Code
    public ResumeDTO getResumeByResumeIdTest(int resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        return mapToResumeDTO(resume);
    }

    // Resume 조회 : Status = T 인 모든 테이블
    public ResumeDTO getResumeByResumeId(int resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        List<Skill> filteredSkills = resume.getSkills().stream()
                .filter(Skill::isStatus)
                .collect(Collectors.toList());
        resume.setSkills(filteredSkills);

        List<Project> filteredProjects = resume.getProjects().stream()
                .filter(Project::isStatus)
                .collect(Collectors.toList());
        resume.setProjects(filteredProjects);

        List<Career> filteredCareers = resume.getCareers().stream()
                .filter(Career::isStatus)
                .collect(Collectors.toList());
        resume.setCareers(filteredCareers);

        //
        // 추가
        //

        return mapToResumeDTO(resume);
    }

    // Resume 생성 : User ID = 1 ------------------------------------------------- Test Code
    public ResumeDTO createResumeTest(String title) {
        int userId = 1;
        User user = userService.getUserByUserId(userId);

        Resume resume = new Resume(user, title);
        resume = resumeRepository.save(resume);

        return mapToResumeDTO(resume);
    }

    // Resume 생성 : 로그인 User
    public ResumeDTO createResume(String title) {
        int userId = userService.getCurrentUserId();
        User user = userService.getUserByUserId(userId);

        Resume resume = new Resume(user, title);
        resume = resumeRepository.save(resume);

        return mapToResumeDTO(resume);
    }

    // Resume 삭제
    public void deleteResume(int resumeId) {
        resumeRepository.deleteById(resumeId);
    }

    // Resume 업데이트 : Title
    public ResumeDTO saveResumeTitleByResumeId(int resumeId, String title) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
        resume.setTitle(title);
        resume = resumeRepository.save(resume);

        return mapToResumeDTO(resume);
    }

    // Resume 업데이트 : Title 포함 테이블 전체
    public ResumeDTO saveResumeDataByResumeId(int resumeId, ResumeDTO resumeDTO) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        // Title 업데이트
        if (resumeDTO.getTitle() != null) {
            resume.setTitle(resumeDTO.getTitle());
        }

        // Skills, Careers, Projects 업데이트 : Status 포함
        if (resumeDTO.getSkills() != null) {
            updateSkills(resume, resumeDTO.getSkills());
        }
        if (resumeDTO.getCareers() != null) {
            updateCareers(resume, resumeDTO.getCareers());
        }
        if (resumeDTO.getProjects() != null) {
            updateProjects(resume, resumeDTO.getProjects());
        }

        resume = resumeRepository.save(resume);

        return mapToResumeDTO(resume);
    }

    // -------------------------------------------------------------------------------------
    // 호출함수
    // -------------------------------------------------------------------------------------

    // Content 를 기준으로 Status 를 True/False 설정
    private void updateSkills(Resume resume, List<SkillDTO> skillDTOs) {
        for (SkillDTO skillDTO : skillDTOs) {
            Skill skill = skillRepository.findById(skillDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
    
            if (skillDTO.getContent() != null && !skillDTO.getContent().equalsIgnoreCase("null")) {
                skill.setTechStack(skillDTO.getTechStack());
                skill.setContent(skillDTO.getContent());
                skill.setStatus(!skillDTO.getContent().isEmpty());
            } else {
                skill.setTechStack(null);
                skill.setContent(null);
                skill.setStatus(false);
            }
    
            skillRepository.save(skill);
        }
    }

    // Content 를 기준으로 Status 를 True/False 설정
    private void updateCareers(Resume resume, List<CareerDTO> careerDTOs) {
        for (CareerDTO careerDTO : careerDTOs) {
            Career career = careerRepository.findById(careerDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Career not found"));
    
            if (careerDTO.getContent() != null && !careerDTO.getContent().equalsIgnoreCase("null")) {
                career.setCompany(careerDTO.getCompany());
                career.setDepartment(careerDTO.getDepartment());
                career.setPeriod(careerDTO.getPeriod());
                career.setIsCurrent(careerDTO.getIsCurrent());
                career.setTechStack(careerDTO.getTechStack());
                career.setContent(careerDTO.getContent());
                career.setStatus(!careerDTO.getContent().isEmpty());
            } else {
                career.setCompany(null);
                career.setDepartment(null);
                career.setPeriod(null);
                career.setIsCurrent(null);
                career.setTechStack(null);
                career.setContent(null);
                career.setStatus(false);
            }
    
            careerRepository.save(career);
        }
    }

    // Content 를 기준으로 Status 를 True/False 설정
    private void updateProjects(Resume resume, List<ProjectDTO> projectDTOs) {
        for (ProjectDTO projectDTO : projectDTOs) {
            Project project = projectRepository.findById(projectDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    
            if (projectDTO.getContent() != null && !projectDTO.getContent().equalsIgnoreCase("null")) {
                project.setTitle(projectDTO.getTitle());
                project.setPeriod(projectDTO.getPeriod());
                project.setIsCurrent(projectDTO.getIsCurrent());
                project.setIntro(projectDTO.getIntro());
                project.setTechStack(projectDTO.getTechStack());
                project.setContent(projectDTO.getContent());
                project.setStatus(!projectDTO.getContent().isEmpty());
            } else {
                project.setTitle(null);
                project.setPeriod(null);
                project.setIsCurrent(null);
                project.setIntro(null);
                project.setTechStack(null);
                project.setContent(null);
                project.setStatus(false);
            }
    
            projectRepository.save(project);
        }
    }

        //
        // 추가
        //

    private ResumeDTO mapToResumeDTO(Resume resume) {
        List<SkillDTO> skills = fetchSkillsForResume(resume.getId());
        List<CareerDTO> careers = fetchCareersForResume(resume.getId());
        List<ProjectDTO> projects = fetchProjectsForResume(resume.getId());

        return new ResumeDTO(
            resume.getId(),
            resume.getUser().getId(),
            resume.getTitle(),
            resume.getCreatedAt(),
            skills,
            careers,
            projects
            //
            // 추가
            //
        );
    }

    private List<SkillDTO> fetchSkillsForResume(int resumeId) {
        return skillRepository.findByResumeId(resumeId).stream()
                .map(skill -> new SkillDTO(
                    skill.getId(), 
                    skill.getResume().getId(), 
                    skill.isStatus(), 
                    skill.getTechStack(), 
                    skill.getContent()
                    )
                )
                .collect(Collectors.toList());
    }
    
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
                    career.getContent()
                    )
                )
                .collect(Collectors.toList());
    }
    
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
                    project.getContent()
                    )
                )
                .collect(Collectors.toList());
    }

    //
    // 추가
    //

}
