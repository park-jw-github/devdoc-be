package com.devdoc.backend.dto;

public class ProjectDTO {
    private int id;
    private int resumeId;
    private boolean status;
    private String title;
    private String period;
    private Boolean isCurrent;
    private String intro;
    private String techStack;
    private String content;

    public ProjectDTO() {}

    public ProjectDTO(int id, int resumeId, boolean status, String title, String period, Boolean isCurrent, String intro, String techStack, String content) {
        this.id = id;
        this.resumeId = resumeId;
        this.status = status;
        this.title = title;
        this.period = period;
        this.isCurrent = isCurrent;
        this.intro = intro;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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
