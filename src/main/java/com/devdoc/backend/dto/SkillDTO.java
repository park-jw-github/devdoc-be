package com.devdoc.backend.dto;

public class SkillDTO {
    private int id;
    private int resumeId;
    private boolean status;
    private String techStack;
    private String content;

    public SkillDTO() {}

    public SkillDTO(int id, int resumeId, boolean status, String techStack, String content) {
        this.id = id;
        this.resumeId = resumeId;
        this.status = status;
        this.techStack = techStack;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
