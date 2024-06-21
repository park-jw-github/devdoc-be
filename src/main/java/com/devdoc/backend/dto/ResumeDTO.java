package com.devdoc.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ResumeDTO {
    private int id;
    private int userId;
    private String title;
    private LocalDateTime createdAt;
    private List<SkillDTO> skills;
    private List<CareerDTO> careers;
    private List<ProjectDTO> projects;

    public ResumeDTO() {}

    public ResumeDTO(int id, String title, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
    }

    public ResumeDTO(int id, int userId, String title, LocalDateTime createdAt, List<SkillDTO> skills, List<CareerDTO> careers, List<ProjectDTO> projects) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.createdAt = createdAt;     // ResumeDTO 에 List<항목들> 넣은 이유
        this.skills = skills;           // 1. /api/resumes/{resumeId}/skills/1~3 로 요청 3번 보내기
        this.careers = careers;         // 2. /api/resumes/{resumeId} 로 요청 [{1},{2},{3}] 한번에 보내기
        this.projects = projects;       // 테이블을 여러개 동시에 보내려면 2번이 적합하다 생각하였음..
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<SkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDTO> skills) {
        this.skills = skills;
    }

    public List<CareerDTO> getCareers() {
        return careers;
    }

    public void setCareers(List<CareerDTO> careers) {
        this.careers = careers;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
