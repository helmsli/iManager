package com.xinwei.process.entity;

import java.util.Date;

public class DepartleaderApproval {
    private Long recordId;

    private Long projectId;

    private String stage;

    private Date time;

    private String conclusion;

    private String comments;

    private Long userId;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	@Override
	public String toString() {
		return "DepartleaderApproval [recordId=" + recordId + ", projectId="
				+ projectId + ", stage=" + stage + ", time=" + time
				+ ", conclusion=" + conclusion + ", comments=" + comments
				+ ", userId=" + userId + "]";
	}
    
}