package com.devdoc.backend.dto;

public class CareerDTO {
    private int id;
    private int resumeId;
    private boolean status;
    private String company;
    private String department;
    private String period;
    private Boolean isCurrent;
    private String techStack;
    private String content;

    public CareerDTO() {}

    public CareerDTO(int id, int resumeId, boolean status, String company, String department, String period, Boolean isCurrent, String techStack, String content) {
        this.id = id;
        this.resumeId = resumeId;
        this.status = status;
        this.company = company;
        this.department = department;
        this.period = period;
        this.isCurrent = isCurrent;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
